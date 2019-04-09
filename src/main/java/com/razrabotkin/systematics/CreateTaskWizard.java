package com.razrabotkin.systematics;

import com.github.cjwizard.*;
import com.github.cjwizard.pagetemplates.TitledPageTemplate;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Мастер создания задания
 */
public class CreateTaskWizard extends JDialog {
    public CreateTaskWizard(){
        // Создаём Мастер
        final WizardContainer wc =
                new WizardContainer(new TestFactory(),
                        new TitledPageTemplate(),
                        new StackWizardSettings());
    }

    /**
     * Реализация PageFactory для генерации страниц Мастера
     */
    private class TestFactory extends AbstractPageFactory {

        // Для простоты создадим массив из трёх страниц
        private final WizardPage[] pages = {
                new WizardPage("One", "First Page"){
                    // Это конструктор анонимного класса.
                    // Конечно же, страницы Мастера не должны быть анонимными. TODO: А какими они должны быть?
                    // Такой способ просто позволяет демо-образцу помещаться в один файл
                    {
                        JTextField field = new JTextField();
                        // Устанавливаем имя каждому компоненту, из которого мы хотим собирать данные.
                        // Это необходимо делать ДО добавления компонента к странице.
                        field.setName("testField");
                        field.setPreferredSize(new Dimension(50, 20));
                        add(new JLabel("One!"));
                        add(field);
                    }
                },
                new WizardPage("Two", "Second Page"){
                    {
                        JCheckBox box = new JCheckBox("testBox");
                        box.setName("box");
                        add(new JLabel("Two!"));
                        add(box);
                    }

                    /* (non-Javadoc)
                     * @see com.github.cjwizard.WizardPage#updateSettings(com.github.cjwizard.WizardSettings)
                     */
                    @Override
                    public void updateSettings(WizardSettings settings) {
                        super.updateSettings(settings);

                        // This is called when the user clicks next, so we could do
                        // some longer-running processing here if we wanted to, and
                        // pop up progress bars, etc.  Once this method returns, the
                        // wizard will continue.  Beware though, this runs in the
                        // event dispatch thread (EDT), and may render the UI
                        // unresponsive if you don't issue a new thread for any long
                        // running ops.  Future versions will offer a better way of
                        // doing this.
                    }

                },
                new WizardPage("Three", "Third Page"){
                    {
                        add(new JLabel("Three!"));
                        setBackground(Color.green);
                    }

                    /**
                     * Это последняя страница Мастера, поэтому необходимо включить кнопку "Готово"
                     * и отключить кнопку "Далее" прямо перед тем, как страница будет загружена
                     */
                    public void rendering(java.util.List<WizardPage> path, WizardSettings settings) {
                        super.rendering(path, settings);
                        setFinishEnabled(true);
                        setNextEnabled(false);
                    }
                }
        };


        /* (non-Javadoc)
         * @see com.github.cjwizard.PageFactory#createPage(java.util.List, com.github.cjwizard.WizardSettings)
         */
        @Override
        public WizardPage createPage(List<WizardPage> path,
                                     WizardSettings settings) {
            log.debug("creating page "+path.size());

            // Получаем следующую страницу для отображения.
            // Объект path - это список всех пройденных ранее страниц,
            // поэтому мы можем легко определить, на каком шаге находится пользователь,
            // получая длину объекта path.
            WizardPage page = pages[path.size()];

            // При желании мы могли бы использовать объект WizardSettings,
            // чтобы изменить последовательность страниц Мастера.

            log.debug("Returning page: "+page);
            return page;
        }
    }
}
