package com.razrabotkin.systematics;

import javax.swing.*;

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
}
