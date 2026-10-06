# 🚚 SpeedFastApp

## 📘 Descripción

SpeedFastApp es una aplicación desarrollada en Java para gestionar repartidores, pedidos y entregas de la empresa SpeedFast

El sistema permite realizar operaciones CRUD mediante una interfaz gráfica desarrollada con Java Swing y utiliza una base de datos MySQL conectada mediante JDBC

## ⚙️ Funcionalidades

### Repartidores
- Registrar repartidores
- Listar repartidores
- Actualizar repartidores
- Eliminar repartidores

### Pedidos
- Registrar pedidos
- Seleccionar tipo de pedido: COMIDA, ENCOMIENDA o EXPRESS
- Seleccionar estado: PENDIENTE, EN_REPARTO o ENTREGADO
- Listar pedidos
- Actualizar pedidos
- Eliminar pedidos

### Entregas
- Registrar entregas
- Asociar un pedido con un repartidor
- Registrar fecha y hora
- Listar entregas
- Actualizar entregas
- Eliminar entregas

## 🗂️ Estructura del proyecto

```text
src/main/java/cl/speedfast
├── app
│   └── Main.java
├── dao
│   ├── RepartidorDao.java
│   ├── PedidoDao.java
│   ├── EntregaDao.java
│   └── impl
│       ├── RepartidorDaoImpl.java
│       ├── PedidoDaoImpl.java
│       └── EntregaDaoImpl.java
├── model
│   ├── Repartidor.java
│   ├── Pedido.java
│   ├── Entrega.java
│   ├── TipoPedido.java
│   └── EstadoPedido.java
├── util
│   └── ConexionDB.java
└── view
    └── SpeedFastFrame.java
