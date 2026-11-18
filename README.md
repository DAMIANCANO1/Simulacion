# Simulacion

Aplicacion Java con Swing para generar numeros pseudoaleatorios y ejecutar pruebas estadisticas basicas.

## Metodos de generacion

- Congruencial aditivo
- Congruencial multiplicativo
- Congruencial cuadratico
- Productos medios
- Cuadrados medios
- Multiplicador constante
- Congruencial lineal

## Pruebas disponibles

- Medias
- Varianza
- Chi-cuadrada
- Kolmogorov-Smirnov
- Corridas arriba y abajo
- Corridas arriba y abajo de la media
- Poker
- Huecos

## Requisitos

- JDK 8 o posterior
- Apache Ant para compilar por consola
- NetBeans con soporte para proyectos Java Ant

No se utilizan frameworks, Maven, Gradle ni librerias externas.

## Abrir en NetBeans

1. Abra NetBeans.
2. Seleccione **Archivo > Abrir proyecto**.
3. Seleccione la carpeta que contiene `build.xml`.
4. Ejecute el proyecto con **Run**.

La clase principal es `simulacion.Main`.

## Compilar y ejecutar desde consola

Windows:

```bat
ant clean jar
java -jar dist\Simulacion.jar
```

## Clonar desde GitHub

```bat
git clone https://github.com/DAMIANCANO1/Simulacion.git
cd Simulacion
ant clean jar
java -jar dist\Simulacion.jar
```
