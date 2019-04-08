//package com.razrabotkin.systematics;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//
///**
// * Класс, который представляет внешнюю оболочку мастера
// */
//public class Wizard {
//    private WizardModel wizardModel;
//    private WizardController wizardController;
//
//    // Диалог
//    private JDialog Wizard;
//
//    // Сменная панель, на которой будут размещаться те или иные компоненты (Component)
//    private JPanel cardPanel;
//
//    // Элемент для размещения сменяемых панелей (Layout Manager)
//    private CardLayout cardLayout;
//
//    // Кнопки
//    private JButton backButton;
//    private JButton nextButton;
//    private JButton cancelButton;
//
//    private int returnCode;
//
//    public Wizard(Frame owner) {
//
//        wizardModel = new WizardModel();
//        Wizard = new JDialog(owner);
//        wizardController = new WizardController(this);
//
//        initComponents();
//    }
//
//    /**
//     * Размещает элементы управления и подключает кнопки к контроллеру
//     */
//    private void initComponents() {
//
//        // Code omitted
//        JPanel buttonPanel = new JPanel();
//        Box buttonBox = new Box(BoxLayout.X_AXIS);
//
//        cardPanel = new JPanel();
//        cardPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
//
//        cardLayout = new CardLayout();
//        cardPanel.setLayout(cardLayout);
//        backButton = new JButton();
//        nextButton = new JButton();
//        cancelButton = new JButton();
//
//        backButton.addActionListener(wizardController);
//        nextButton.addActionListener(wizardController);
//        cancelButton.addActionListener(wizardController);
//
//        buttonPanel.setLayout(new BorderLayout());
//        buttonPanel.add(new JSeparator(), BorderLayout.NORTH);
//
//        buttonBox.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
//        buttonBox.add(backButton);
//        buttonBox.add(Box.createHorizontalStrut(10));
//        buttonBox.add(nextButton);
//        buttonBox.add(Box.createHorizontalStrut(30));
//        buttonBox.add(cancelButton);
//        buttonPanel.add(buttonBox, java.awt.BorderLayout.EAST);
//        Wizard.getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
//        Wizard.getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);
//
//    }
//
//    /**
//     * Регистрирует сменные панели Лейаут-менеджером и моделью
//     * @param id
//     * @param panel
//     */
//    public void registerWizardPanel(Object id,
//                                    WizardPanelDescriptor panel)
//    {
//        // TODO: Шо это такое?
//        cardPanel.add(panel.getPanelComponent(), id);
//        wizardModel.registerPanel(id, panel);
//    }
//
//    void setBackButtonEnabled(boolean b) {
//        backButton.setEnabled(b);
//    }
//
//    void setNextButtonEnabled(boolean b) {
//        nextButton.setEnabled(b);
//    }
//
//    public void setCurrentPanel(Object id) {
//
//        // Code omitted
//
//        WizardPanelDescriptor oldPanelDescriptor =
//                wizardModel.getCurrentPanelDescriptor();
//
//        if (oldPanelDescriptor != null)
//            oldPanelDescriptor.aboutToHidePanel();
//
//        wizardModel.setCurrentPanel(id);
//
//        wizardModel.getCurrentPanelDescriptor().
//                AboutToDisplayPanel();
//
//        cardLayout.show(cardPanel, id.toString());
//
//        wizardModel.getCurrentPanelDescriptor().
//                DisplayingPanel();
//
//    }
//}
