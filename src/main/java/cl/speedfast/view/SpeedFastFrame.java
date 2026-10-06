package cl.speedfast.view;


import cl.speedfast.dao.PedidoDao;
import cl.speedfast.dao.impl.PedidoDaoImpl;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.TipoPedido;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.dao.RepartidorDao;
import cl.speedfast.dao.impl.RepartidorDaoImpl;
import cl.speedfast.model.Repartidor;
import cl.speedfast.dao.EntregaDao;
import cl.speedfast.dao.impl.EntregaDaoImpl;
import cl.speedfast.model.Entrega;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SpeedFastFrame extends JFrame {

    private final RepartidorDao repartidorDao = new RepartidorDaoImpl();

    private final JTextField txtNombre = new JTextField(15);

    private final DefaultTableModel modeloRepartidores =
            new DefaultTableModel(
                    new String[]{"ID", "Nombre"}, 0
            );

    private final JTable tablaRepartidores =
            new JTable(modeloRepartidores);

    private final PedidoDao pedidoDao = new PedidoDaoImpl();

    private final JTextField txtDireccion = new JTextField(15);

    private final JComboBox<TipoPedido> comboTipo =
            new JComboBox<>(TipoPedido.values());

    private final JComboBox<EstadoPedido> comboEstado =
            new JComboBox<>(EstadoPedido.values());

    private final DefaultTableModel modeloPedidos =
            new DefaultTableModel(
                    new String[]{"ID", "Dirección", "Tipo", "Estado"}, 0
            );

    private final JTable tablaPedidos =
            new JTable(modeloPedidos);

    // ENTREGAS
    private final EntregaDao entregaDao = new EntregaDaoImpl();

    private final JComboBox<Pedido> comboPedido =
            new JComboBox<>();

    private final JComboBox<Repartidor> comboRepartidor =
            new JComboBox<>();

    private final JTextField txtFecha =
            new JTextField(10);

    private final JTextField txtHora =
            new JTextField(6);

    private final DefaultTableModel modeloEntregas =
            new DefaultTableModel(
                    new String[]{"ID", "Pedido", "Repartidor", "Fecha", "Hora"}, 0
            );

    private final JTable tablaEntregas =
            new JTable(modeloEntregas);

    public SpeedFastFrame() {

        super("SpeedFast");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new GridLayout(3, 1));

        // =========================
        // REPARTIDORES
        // =========================

        JPanel panelRepartidores = new JPanel(new BorderLayout());

        JPanel formularioRepartidores = new JPanel();

        formularioRepartidores.add(new JLabel("Nombre:"));
        formularioRepartidores.add(txtNombre);

        JButton btnCrearRepartidor = new JButton("Crear");
        JButton btnActualizarRepartidor = new JButton("Actualizar");
        JButton btnEliminarRepartidor = new JButton("Eliminar");

        formularioRepartidores.add(btnCrearRepartidor);
        formularioRepartidores.add(btnActualizarRepartidor);
        formularioRepartidores.add(btnEliminarRepartidor);

        panelRepartidores.setBorder(
                BorderFactory.createTitledBorder("Repartidores")
        );

        panelRepartidores.add(
                formularioRepartidores,
                BorderLayout.NORTH
        );

        panelRepartidores.add(
                new JScrollPane(tablaRepartidores),
                BorderLayout.CENTER
        );

        btnCrearRepartidor.addActionListener(
                e -> crear()
        );

        btnActualizarRepartidor.addActionListener(
                e -> actualizar()
        );

        btnEliminarRepartidor.addActionListener(
                e -> eliminar()
        );

        tablaRepartidores.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila =
                            tablaRepartidores.getSelectedRow();

                    if (fila >= 0) {

                        txtNombre.setText(
                                modeloRepartidores
                                        .getValueAt(fila, 1)
                                        .toString()
                        );
                    }
                });

        // =========================
        // PEDIDOS
        // =========================

        JPanel panelPedidos = new JPanel(new BorderLayout());

        JPanel formularioPedidos = new JPanel();

        formularioPedidos.add(new JLabel("Dirección:"));
        formularioPedidos.add(txtDireccion);

        formularioPedidos.add(new JLabel("Tipo:"));
        formularioPedidos.add(comboTipo);

        formularioPedidos.add(new JLabel("Estado:"));
        formularioPedidos.add(comboEstado);

        JButton btnCrearPedido =
                new JButton("Crear");

        JButton btnActualizarPedido =
                new JButton("Actualizar");

        JButton btnEliminarPedido =
                new JButton("Eliminar");

        formularioPedidos.add(btnCrearPedido);
        formularioPedidos.add(btnActualizarPedido);
        formularioPedidos.add(btnEliminarPedido);

        panelPedidos.setBorder(
                BorderFactory.createTitledBorder("Pedidos")
        );

        panelPedidos.add(
                formularioPedidos,
                BorderLayout.NORTH
        );

        panelPedidos.add(
                new JScrollPane(tablaPedidos),
                BorderLayout.CENTER
        );

        btnCrearPedido.addActionListener(
                e -> crearPedido()
        );

        btnActualizarPedido.addActionListener(
                e -> actualizarPedido()
        );

        btnEliminarPedido.addActionListener(
                e -> eliminarPedido()
        );

        tablaPedidos.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila =
                            tablaPedidos.getSelectedRow();

                    if (fila >= 0) {

                        txtDireccion.setText(
                                modeloPedidos
                                        .getValueAt(fila, 1)
                                        .toString()
                        );

                        comboTipo.setSelectedItem(
                                TipoPedido.valueOf(
                                        modeloPedidos
                                                .getValueAt(fila, 2)
                                                .toString()
                                )
                        );

                        comboEstado.setSelectedItem(
                                EstadoPedido.valueOf(
                                        modeloPedidos
                                                .getValueAt(fila, 3)
                                                .toString()
                                )
                        );
                    }
                });

        // =========================
        // ENTREGAS
       // =========================

        JPanel panelEntregas = new JPanel(new BorderLayout());

        JPanel formularioEntregas = new JPanel();

        formularioEntregas.add(new JLabel("Pedido:"));
        formularioEntregas.add(comboPedido);

        formularioEntregas.add(new JLabel("Repartidor:"));
        formularioEntregas.add(comboRepartidor);

        formularioEntregas.add(new JLabel("Fecha:"));
        formularioEntregas.add(txtFecha);

        formularioEntregas.add(new JLabel("Hora:"));
        formularioEntregas.add(txtHora);

        JButton btnCrearEntrega = new JButton("Crear");
        JButton btnActualizarEntrega = new JButton("Actualizar");
        JButton btnEliminarEntrega = new JButton("Eliminar");

        formularioEntregas.add(btnCrearEntrega);
        formularioEntregas.add(btnActualizarEntrega);
        formularioEntregas.add(btnEliminarEntrega);

        panelEntregas.setBorder(
                BorderFactory.createTitledBorder("Entregas")
        );

        panelEntregas.add(
                formularioEntregas,
                BorderLayout.NORTH
        );

        panelEntregas.add(
                new JScrollPane(tablaEntregas),
                BorderLayout.CENTER
        );

        btnCrearEntrega.addActionListener(
                e -> crearEntrega()
        );

        btnActualizarEntrega.addActionListener(
                e -> actualizarEntrega()
        );

        btnEliminarEntrega.addActionListener(
                e -> eliminarEntrega()
        );

        tablaEntregas.getSelectionModel()
                .addListSelectionListener(e -> {

                    int fila = tablaEntregas.getSelectedRow();

                    if (fila >= 0) {

                        int idPedido = Integer.parseInt(
                                modeloEntregas
                                        .getValueAt(fila, 1)
                                        .toString()
                        );

                        int idRepartidor = Integer.parseInt(
                                modeloEntregas
                                        .getValueAt(fila, 2)
                                        .toString()
                        );

                        for (int i = 0; i < comboPedido.getItemCount(); i++) {

                            Pedido pedido = comboPedido.getItemAt(i);

                            if (pedido.getId() == idPedido) {
                                comboPedido.setSelectedIndex(i);
                            }
                        }

                        for (int i = 0; i < comboRepartidor.getItemCount(); i++) {

                            Repartidor repartidor =
                                    comboRepartidor.getItemAt(i);

                            if (repartidor.getId() == idRepartidor) {
                                comboRepartidor.setSelectedIndex(i);
                            }
                        }

                        txtFecha.setText(
                                modeloEntregas
                                        .getValueAt(fila, 3)
                                        .toString()
                        );

                        txtHora.setText(
                                modeloEntregas
                                        .getValueAt(fila, 4)
                                        .toString()
                        );
                    }
                });

        panelPrincipal.add(panelRepartidores);
        panelPrincipal.add(panelPedidos);
        panelPrincipal.add(panelEntregas);

        add(panelPrincipal);

        cargar();
        cargarPedidos();
        cargarCombosEntrega();
        cargarEntregas();
    }

    private void cargar() {


        try {
            modeloRepartidores.setRowCount(0);

            for (Repartidor repartidor : repartidorDao.readAll()) {
                modeloRepartidores.addRow(
                        new Object[]{
                                repartidor.getId(),
                                repartidor.getNombre()
                        }
                );
            }

        } catch (Exception e) {

            error(e);
        }
    }

    private void crear() {

        try {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un nombre.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Repartidor repartidor =
                    new Repartidor(
                            txtNombre.getText().trim()
                    );

            repartidorDao.create(repartidor);

            limpiar();
            cargar();
            cargarCombosEntrega();

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            error(e);
        }
    }

    private void actualizar() {

        int fila = tablaRepartidores.getSelectedRow();

        if (fila < 0) {
            return;
        }

        try {

            int id = Integer.parseInt(
                    tablaRepartidores
                            .getValueAt(fila, 0)
                            .toString()
            );

            Repartidor repartidor =
                    new Repartidor(
                            id,
                            txtNombre.getText().trim()
                    );

            repartidorDao.update(repartidor);

            limpiar();
            cargar();
            cargarCombosEntrega();

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void eliminar() {

        int fila = tablaRepartidores.getSelectedRow();

        if (fila < 0) {
            return;
        }

        try {

            int id = Integer.parseInt(
                    tablaRepartidores
                            .getValueAt(fila, 0)
                            .toString()
            );

            repartidorDao.delete(id);

            limpiar();
            cargar();
            cargarCombosEntrega();

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void limpiar() {

        txtNombre.setText("");
    }

    private void cargarPedidos() {

        try {

            modeloPedidos.setRowCount(0);

            for (Pedido pedido : pedidoDao.readAll()) {

                modeloPedidos.addRow(
                        new Object[]{
                                pedido.getId(),
                                pedido.getDireccion(),
                                pedido.getTipo(),
                                pedido.getEstado()
                        }
                );
            }

        } catch (Exception e) {

            error(e);
        }
    }

    private void crearPedido() {

        try {

            if (txtDireccion.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar una dirección.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Pedido pedido = new Pedido(
                    txtDireccion.getText().trim(),
                    (TipoPedido) comboTipo.getSelectedItem(),
                    (EstadoPedido) comboEstado.getSelectedItem()
            );

            pedidoDao.create(pedido);

            limpiarPedido();
            cargarPedidos();
            cargarCombosEntrega();

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido creado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void actualizarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila < 0) {
            return;
        }

        try {

            int id = Integer.parseInt(
                    tablaPedidos
                            .getValueAt(fila, 0)
                            .toString()
            );

            Pedido pedido = new Pedido(
                    id,
                    txtDireccion.getText().trim(),
                    (TipoPedido) comboTipo.getSelectedItem(),
                    (EstadoPedido) comboEstado.getSelectedItem()
            );

            pedidoDao.update(pedido);

            limpiarPedido();
            cargarPedidos();
            cargarCombosEntrega();

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void eliminarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila < 0) {
            return;
        }

        try {

            int id = Integer.parseInt(
                    tablaPedidos
                            .getValueAt(fila, 0)
                            .toString()
            );

            pedidoDao.delete(id);

            limpiarPedido();
            cargarPedidos();
            cargarCombosEntrega();

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void limpiarPedido() {

        txtDireccion.setText("");

        comboTipo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
    }

    private void cargarCombosEntrega() {

        try {

            comboPedido.removeAllItems();
            comboRepartidor.removeAllItems();

            for (Pedido pedido : pedidoDao.readAll()) {
                comboPedido.addItem(pedido);
            }

            for (Repartidor repartidor : repartidorDao.readAll()) {
                comboRepartidor.addItem(repartidor);
            }

        } catch (Exception e) {

            error(e);
        }
    }

    private void cargarEntregas() {

        try {

            modeloEntregas.setRowCount(0);

            for (Entrega entrega : entregaDao.readAll()) {

                String fecha =
                        String.format(
                                "%02d-%02d-%04d",
                                entrega.getFecha().getDayOfMonth(),
                                entrega.getFecha().getMonthValue(),
                                entrega.getFecha().getYear()
                        );

                modeloEntregas.addRow(
                        new Object[]{
                                entrega.getId(),
                                entrega.getPedido().getId(),
                                entrega.getRepartidor().getId(),
                                fecha,
                                entrega.getHora()
                        }
                );
            }

        } catch (Exception e) {

            error(e);
        }
    }

    private void crearEntrega() {

        try {

            if (comboPedido.getSelectedItem() == null
                    || comboRepartidor.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un pedido y un repartidor.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (txtFecha.getText().trim().isEmpty()
                    || txtHora.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar fecha y hora.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Pedido pedido =
                    (Pedido) comboPedido.getSelectedItem();

            Repartidor repartidor =
                    (Repartidor) comboRepartidor.getSelectedItem();

            String[] partesFecha =
                    txtFecha.getText().trim().split("-");

            int dia = Integer.parseInt(partesFecha[0]);
            int mes = Integer.parseInt(partesFecha[1]);
            int anio = Integer.parseInt(partesFecha[2]);

            LocalDate fecha =
                    LocalDate.of(anio, mes, dia);

            LocalTime hora =
                    LocalTime.parse(txtHora.getText().trim());

            Entrega entrega =
                    new Entrega(
                            pedido,
                            repartidor,
                            fecha,
                            hora
                    );

            entregaDao.create(entrega);

            limpiarEntrega();
            cargarEntregas();

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega creada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void actualizarEntrega() {

        int fila = tablaEntregas.getSelectedRow();

        if (fila < 0) {
            return;
        }

        try {

            int id = Integer.parseInt(
                    tablaEntregas
                            .getValueAt(fila, 0)
                            .toString()
            );

            Pedido pedido =
                    (Pedido) comboPedido.getSelectedItem();

            Repartidor repartidor =
                    (Repartidor) comboRepartidor.getSelectedItem();

            String[] partesFecha =
                    txtFecha.getText().trim().split("-");

            int dia = Integer.parseInt(partesFecha[0]);
            int mes = Integer.parseInt(partesFecha[1]);
            int anio = Integer.parseInt(partesFecha[2]);

            LocalDate fecha =
                    LocalDate.of(anio, mes, dia);

            LocalTime hora =
                    LocalTime.parse(txtHora.getText().trim());

            Entrega entrega =
                    new Entrega(
                            id,
                            pedido,
                            repartidor,
                            fecha,
                            hora
                    );

            entregaDao.update(entrega);

            limpiarEntrega();
            cargarEntregas();

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void eliminarEntrega() {

        int fila = tablaEntregas.getSelectedRow();

        if (fila < 0) {
            return;
        }

        try {

            int id = Integer.parseInt(
                    tablaEntregas
                            .getValueAt(fila, 0)
                            .toString()
            );

            entregaDao.delete(id);

            limpiarEntrega();
            cargarEntregas();

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            error(e);
        }
    }

    private void limpiarEntrega() {

        txtFecha.setText("");
        txtHora.setText("");
    }

    private void error(Exception e) {

        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}