<?php
/**
 * ${module}
 *
 * @license GNU/LGPLv3 (or at your option, any later version).
 * @package ${module}
 */

/**
 * ${type} form handler.
 */
class ${module}_Form_${type} extends Zikula_Form_Handler
{
    /**
     * Setup form.
     *
     * @param Zikula_Form_View $view Current Zikula_Form_View instance.
     *
     * @return boolean
     */
    public function initialize(Zikula_Form_View $view)
    {
        // TODO: implement initialize()

        return true;
    }

    /**
     * Handle form submission.
     *
     * @param Zikula_Form_View $view  Current Zikula_Form_View instance.
     * @param array            &$args Args.
     *
     * @return boolean
     */
    public function handleCommand(Zikula_Form_View $view, &$args)
    {
        // check for valid form
        if (!$view->isValid()) {
            return false;
        }

        // load form values
        $data = $view->getValues();

        // TODO: implement handleCommand()

        return true;
    }
}
