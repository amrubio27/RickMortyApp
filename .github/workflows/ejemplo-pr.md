# 🏗️ refactor(testing): introducción de Test Data Builders y Object Mother pattern para generación de datos de test

## 📌 Resumen

Esta PR introduce una **estrategia centralizada y reutilizable** para construir objetos de test
mediante el patrón **Builder** y **Object Mother**.

En lugar de hardcodear datos de prueba en cada test o duplicar construcciones de objetos, ahora
disponemos de:

- **Builders fluidos** (`ProductBuilder`, `CartItemBuilder`, `PromotionBuilder`) que permiten crear
  objetos con valores por defecto pero personalizables,
- **Object Mothers** (`ProductMother`, `PromotionMother`, `ProductListUiStateMother`) que ofrecen
  instancias de negocio comunes preconstruidas,
- una **base único y centralizada** en `src/sharedTest/java` accesible desde unit tests y
  androidTest.

Esto reduce duplicación, mejora legibilidad, facilita evolución futura y convierte los tests en *
*documentación ejecutable** del comportamiento esperado del dominio.

---

## 🎯 Objetivo de los cambios

El propósito es establecer una **infraestructura de testing escalable y mantenible** donde:

- los datos de test se construyan de forma **consistente y reutilizable**,
- los valores por defecto reflejen el comportamiento real del dominio,
- los tests sean **legibles** y expresivos, sin ruido de construcción de objetos,
- y los cambios en modelos de dominio se reflejen en un único punto (Builders/Mothers).

---

## 🧩 Problema que se resuelve

Antes de esta PR, la creación de datos de test en tests sufría varios problemas:

1. **Duplicación**: cada test construía los mismos objetos una y otra vez.
2. **Fragilidad**: cambios en constructores afectaban a múltiples tests.
3. **Inconsistencia**: diferentes tests podían usar distintos valores por defecto para el mismo
   concepto.
4. **Ilegibilidad**: código de construcción de objetos mezclado con lógica de test.
5. **Mantenimiento**: cambios en el dominio requerían actualizar decenas de lugares.

Con esta PR esos problemas desaparecen porque hay un **única fuente de verdad** para cada estructura
de datos de test.

---

## ✨ Cambios principales

### 1) `ProductBuilder` - Constructor fluido para `Product`

Archivo: `app/src/sharedTest/java/com/amrubio27/cursotestingandroid/core/builders/ProductBuilder.kt`

```kotlin
class ProductBuilder {
    private var id: String = "product-1"
    private var name: String = "Producto de pruebas"
    private var price: Double = 10.0
    private var stock: Int = 10
    private var category: String = "Categoría de pruebas"
    // ... más propiedades

    fun withId(id: String) = apply { this.id = id }
    fun withName(name: String) = apply { this.name = name }
    // ... más métodos fluidos

    fun build() = Product(id, name, ..., category)
}

fun product(block: ProductBuilder.() -> Unit = {}) = ProductBuilder().apply(block).build()
```

#### ¿Por qué este patrón?

- **Valores por defecto sensatos**: un `Product` siempre tiene valores sin necesidad de parámetros.
- **API fluida**: `product { withId("x"); withPrice(5.0) }` es clara e intuitiva.
- **Extensibilidad**: cuando el modelo crece, solo añades métodos en el builder.
- **Documentación implícita**: los valores por defecto comunican qué es "normal" para un producto.

#### Ejemplo de uso en tests:

```kotlin
val testProduct = product {
    withId("milk-1")
    withName("Leche Entera")
    withPrice(2.50)
    withCategory("lacteo")
}
```

---

### 2) `CartItemBuilder` - Constructor fluido para `CartItem`

Archivo:
`app/src/sharedTest/java/com/amrubio27/cursotestingandroid/core/builders/CartItemBuilder.kt`

Similar a `ProductBuilder`, pero más simple:

```kotlin
fun cartItem(block: CartItemBuilder.() -> Unit = {}) = CartItemBuilder().apply(block).build()
```

#### ¿Cuándo usar?

En tests donde necesites validar comportamiento del carrito:

```kotlin
val item = cartItem {
    withProductId("p1")
    withQuantity(3)
}
```

---

### 3) `PromotionBuilder` - Constructor fluido para `Promotion`

Archivo:
`app/src/sharedTest/java/com/amrubio27/cursotestingandroid/core/builders/PromotionBuilder.kt`

```kotlin
fun promotion(block: PromotionBuilder.() -> Unit = {}) = PromotionBuilder().apply(block).build()
```

#### ¿Por qué es valioso?

Las promociones tienen múltiples propiedades (`type`, `value`, `buyQuantity`, fechas de validez). El
builder encapsula:

- valores por defecto para una promoción válida,
- métodos para personalizar cada aspecto,
- control claro del ciclo de vida de la promoción (empezó hace 1 hora, termina en 1 hora).

Esto es especialmente útil para tests de promociones activas/expiradas.

---

### 4) `ProductMother` - Instancias de negocio comunes

Archivo: `app/src/sharedTest/java/com/amrubio27/cursotestingandroid/core/mothers/ProductMother.kt`

```kotlin
object ProductMother {
    fun bread(stock: Int = 8) = product {
        withId("id-bread")
        withName("Pan")
        withCategory("bread")
        withPrice(2.50)
        withStock(stock)
    }

    fun milk(stock: Int = 3) = product {
        withId("id-milk")
        withName("Leche")
        withCategory("lacteo")
        withPrice(1.50)
        withStock(stock)
    }

    fun coffee(stock: Int = 2) = product {
        withId("id-coffee")
        withName("Café")
        withCategory("drinks")
        withPrice(4.50)
        withStock(stock)
    }
}
```

#### ¿Qué es el patrón Object Mother?

El **Object Mother** es un patrón que proporciona **instancias preconstruidas** de objetos de test
con valores realistas del dominio.

Beneficios:

- **Documentación de cambios**: cambiar `ProductMother.bread()` automáticamente afecta todos los
  tests que lo usan.
- **Nombres significativos**: `ProductMother.bread()` es muchísimo más claro que
  `product { withId("123"); withName("Pan"); ... }`.
- **Reutilización masiva**: todos los tests pueden usar `ProductMother.milk()` sin duplicación.
- **Parámetros opcionales**: `bread(stock: Int = 8)` permite personalizar aspectos específicos sin
  perder defaults.

#### Ejemplo de uso:

```kotlin
// En test:
val bread = ProductMother.bread()
val lowStockCoffee = ProductMother.coffee(stock = 0)
```

---

### 5) `PromotionMother` - Instancias de promociones comunes

Archivo: `app/src/sharedTest/java/com/amrubio27/cursotestingandroid/core/mothers/PromotionMother.kt`

```kotlin
object PromotionMother {
    fun percent(
        percent: Double = 25.0,
        discountedPrice: Double = 4.65
    ) = ProductPromotion.Percent(percent, discountedPrice)
}
```

#### ¿Por qué es importante?

Las promociones son complejas y hay diferentes tipos (`Percent`, `BuyXPayY`). El mother:

- proporciona **promociones válidas por defecto**,
- evita errores de construcción,
- y documenta qué descuentos son "típicos" en tests.

---

### 6) `ProductListUiStateMother` - Estados de UI preconstruidos

Archivo:
`app/src/sharedTest/java/com/amrubio27/cursotestingandroid/core/mothers/uistate/ProductListUiStateMother.kt`

```kotlin
object ProductListUiStateMother {
    fun success(
        products: List<ProductWithPromotion> = listOf(
            ProductWithPromotion(ProductMother.coffee(), PromotionMother.percent()),
            ProductWithPromotion(ProductMother.bread()),
            ProductWithPromotion(ProductMother.milk()),
        ),
        categories: List<String> = listOf("bread", "drinks", "lacteo"),
        selectedCategory: String? = null,
        sortOption: SortOption = SortOption.NONE
    ) = ProductListUiState.Success(products, categories, selectedCategory, sortOption)
}
```

#### ¿Por qué vale la pena?

Este mother **combina múltiples Mothers** (producto, promoción) para ofrecer un
`ProductListUiState.Success` realista por defecto.

Beneficios:

- **Test de UI simplificado**: en `ProductListScreenTest` ahora puedes pasar
  `ProductListUiStateMother.success()` sin escribir todo desde cero.
- **Coherencia entre datos**: el estado preconstruido tiene relaciones válidas (productos reales,
  categorías que existen, etc.).
- **Documentación del estado**: los valores por defecto enseñan cómo se vería una pantalla "normal".

Mirar cómo se usa en `ProductListScreenTest`:

```kotlin
private fun createProductListScreen(
    uiState: ProductListUiState = ProductListUiStateMother.success()  // ← Aquí
    // ...
)
```

---

## 🔄 Relación entre Builders y Mothers

```
┌─────────────────┐
│  ProductBuilder │  ← Construcción flexible
└────────────────┬┘
                 │ usado por
┌────────────────▼─────────────┐
│  ProductMother.bread()      │  ← Instancia preconstruida
│  ProductMother.milk()       │
└──────────────────────────────┘
                 │ usado por
┌────────────────▼────────────────────────┐
│ ProductListUiStateMother.success()     │  ← Estado de UI completo
└───────────────────────────────────────┘
                 │ usado por
┌────────────────▼──────────────────────────┐
│ ProductListScreenTest & otros tests     │
└────────────────────────────────────────┘
```

**Flujo**:

1. Builders proporcionan la **capacidad de construcción flexible**.
2. Mothers encapsulan construcciones "canónicas" de negocio.
3. UI State Mothers combinan elementos de negocio para estados completos.
4. Tests usan los Mothers para armar scaffolds rápidamente.

---

## 📁 Ubicación en `sharedTest`

Todos estos archivos viven en `src/sharedTest/java`, lo que significa:

- ✅ accesibles desde `unit tests` (sin Android)
- ✅ accesibles desde `androidTest` (con Android)
- ✅ centralizados en una única fuente de verdad
- ✅ No duplicados entre suites de test

---

## ✅ Beneficios concretos

### 1. Reducción de duplicación

Antes: cada test duplicaba construcción de objetos.  
Ahora: reutilizan `ProductMother.bread()`, `cartItem { ... }`.

### 2. Mantenibilidad mejorada

Si `Product` cambia (nueva propiedad), actualizar `ProductBuilder` afecta todos los tests
automáticamente.

### 3. Claridad y expresividad

```kotlin
// Claro
val product = ProductMother.bread(stock = 0)

// vs. sin mother
val product = Product("id-bread", "Pan", "...", 2.50, 0, null, "bread")
```

### 4. Documentación ejecutable

Los valores en Mothers documentan qué es "normal" en el dominio:

- Pan cuesta 2.50 y tiene 8 unidades por defecto.
- Leche cuesta 1.50 y tiene 3 unidades por defecto.

### 5. Tests más legibles

`ProductListScreenTest` ahora usa `ProductListUiStateMother.success()` en lugar de construir a mano
todo el estado.

---

## 🧪 Ejemplo práctico: uso en `ProductListScreenTest`

```kotlin
class ProductListScreenTest {
    private fun createProductListScreen(
        uiState: ProductListUiState = ProductListUiStateMother.success(),
        // ...
    ) {
        // Antes: tenías que construir manualmente
        // val uiState = ProductListUiState.Success(
        //     products = listOf(
        //         ProductWithPromotion(
        //             Product("id-1", "Pan", ..., 2.50, 8, null, "bread"),
        //             null
        //         ),
        //         ...
        //     ),
        //     categories = listOf("bread", "drinks", "lacteo"),
        //     selectedCategory = null,
        //     sortOption = SortOption.NONE
        // )

        // Ahora: una línea legible
        composeRule.setContent {
            ProductListContent(
                uiState = uiState,
                // ...
            )
        }
    }
}
```

---

## 🏗️ Arquitectura de la pirámide de Mothers

```
Level 3: Pantalla/Estado Completo
┌──────────────────────────────────┐
│ ProductListUiStateMother         │
└────────┬─────────────────┬────────┘
         │                 │
Level 2: Dominio
┌────────▼──────┐    ┌─────▼──────────┐
│ ProductMother │    │ PromotionMother│
└────────┬──────┘    └─────┬──────────┘
         │                 │
Level 1: Construcción Flexible
┌────────▼────────────────────────┐
│ Builders (Product, CartItem...) │
└─────────────────────────────────┘
```

---

## 📝 Recomendaciones para próximos pasos

1. **Crear Mothers para todos los estados UI** (`CartUiStateMother`, `DetailUiStateMother`, etc.)
2. **Extender Builders con tipos intermedios** si surgen patrones (ej.
   `ProductBuilder.withMinimalFields()`)
3. **Documentar en README** la estrategia de Builders/Mothers para nuevos desarrolladores.
4. **Considerar Factories** si la lógica de construcción se vuelve muy compleja (patrón
   complementario a Mothers).
5. **Validar coherencia** en Mothers: si cambias valores, asegúrate de que las relaciones sigan
   siendo válidas.

---

## 🎓 Lecciones clave

- **Builders** son flexibles y reutilizables para construir variaciones de objetos.
- **Mothers** son especializados y ofrecen instancias "canónicas" del dominio.
- Combinar ambos patrones ofrece lo mejor de ambos mundos: **flexibilidad + claridad**.
- Cuando esté centralizado en `sharedTest`, es accesible desde ambas suites de test sin duplicación.

---

## Conclusión

Esta PR introduce una **infraestructura profesional de test data** que hace tests más legibles,
mantenibles y expresivos. No es solo una refactorización: es una **inversión en escalabilidad** del
testing que pagará dividendos conforme el proyecto crezca.

La introducción de Builders y Mothers transforma tests de "cómo construyo objetos" a "qué estoy
probando", mejorando significativamente la calidad y mantenibilidad de la suite de pruebas.