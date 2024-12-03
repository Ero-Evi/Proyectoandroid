# Android App - Proyecto UpSushi -
Mapa, Menú , Pago , Contacto  y Opciones.

## Descripción

Este proyecto es una aplicación Android que ofrece varias funcionalidades esenciales para un negocio local, entre ellas:
- **Mapa**: Visualización de la ubicación de los locales.
- **Menú**: Acceso al menú del local en formato PDF.
- **Pago**: Un botón simple para realizar pagos.
- **Contacto**: Visualización de los números de contacto para llamar o enviar mensajes por WhatsApp a los diferentes locales.
- **Opciones de Usuario**: Gestión de datos del usuario, preguntas frecuentes, acceso a redes sociales y la opción de cerrar sesión.

## Funcionalidades

1. **Mapa**:
   - Visualización de las ubicaciones de los locales.
   - Uso de la API de Google Maps para mostrar los locales en un mapa interactivo.

2. **Menú**:
   - Acceso al **menú del local** en formato PDF.
   - Opción para visualizar el menú dentro de la aplicación o descargarlo para revisión offline.

3. **Pago**:
   - Botón de pago simple que redirige a la pasarela de pago correspondiente o confirma la acción dentro de la app.

4. **Contacto/Chat**:
   - Números de teléfono para **llamar directamente** a los locales.
   - Botón para enviar un **mensaje por WhatsApp** a cada local.

5. **Opciones de Usuario**:
   - **Mis Datos**: Visualización y edición de la información personal.
   - **Preguntas Frecuentes (FAQ)**: Respuestas a las preguntas más comunes.
   - **Redes Sociales**: Enlaces directos a Instagram y Facebook del negocio.
   - **Cerrar Sesión**: Funcionalidad para cerrar sesión de manera segura.

## Instalación

1. Clonar este repositorio:
   ```bash
   git clone https://github.com/Ero-Evi/Proyectoandroid.git
Abrir el proyecto en Android Studio.

Asegurarse de que las siguientes dependencias estén agregadas en el archivo build.gradle:

gradle
Copiar código
dependencies {
    implementation 'com.google.android.gms:play-services-maps:18.0.2'  // API de Google Maps
    implementation 'com.github.barteksc:android-pdf-viewer:3.2.0-beta.5'  // Visualizador de PDF
    implementation 'com.android.support:design:28.0.0'  // Componentes de diseño para el menú y opciones
}
Configurar las claves API necesarias:

Google Maps API: Obtener una clave API desde Google Cloud Console y agregarla en el archivo AndroidManifest.xml dentro del tag <application>.
Ejecutar la aplicación en un emulador o dispositivo físico.

Uso
- Mapa: Accede al mapa desde el menú principal para ver las ubicaciones de los locales.
- Menú PDF: Accede al menú en formato PDF desde la sección de menú y consulta los productos y precios del local.
- Pago: Usa el botón de pago para confirmar la compra o realizar el pago a través de una pasarela.
- Contacto: Llama directamente a los locales o envíales un mensaje por WhatsApp desde la sección de contacto.
- Opciones de Usuario: Gestiona tus datos, consulta preguntas frecuentes, accede a redes sociales o cierra sesión desde esta sección.
- 
Tecnologías Utilizadas
- Java/Kotlin: Lenguajes de programación principales.
- Android Studio: Entorno de desarrollo integrado (IDE) para Android.
- Google Maps API: Para la funcionalidad de mapa.
- *Android PDF Viewer: Para visualizar el menú en formato PDF.* Por Agregar
- *Intents para llamadas y WhatsApp: Permite la interacción con aplicaciones externas para llamadas y mensajería.* Por Agregar





![image](https://github.com/user-attachments/assets/1289d447-ab8d-43c1-830e-9ab917b46eca)

![image](https://github.com/user-attachments/assets/c37d5c8c-b26e-4438-bcf8-2a10e1910002)
![image](https://github.com/user-attachments/assets/9f389303-d64d-4366-92e3-8449c0112942)
![image](https://github.com/user-attachments/assets/fe586a06-9375-4f05-b1d5-88c2fa664498)
![image](https://github.com/user-attachments/assets/adf98cf9-779f-422a-9c82-a10771a271d3)
![image](https://github.com/user-attachments/assets/28a36459-e0b9-42de-ba73-2d67ef82c67d)
![image](https://github.com/user-attachments/assets/c77cdb50-9552-4ee0-b189-9295b8d9242d)
![image](https://github.com/user-attachments/assets/78df2581-f4d4-4480-891b-97308de32417)
![image](https://github.com/user-attachments/assets/d7488aed-c9ee-4c44-b26e-b6425af8e96a)
