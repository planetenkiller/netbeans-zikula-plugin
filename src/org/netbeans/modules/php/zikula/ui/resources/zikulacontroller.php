<?php
/**
 * ${module}
 *
 * @license GNU/LGPLv3 (or at your option, any later version).
 * @package ${module}
 */

/**
 * ${type} controller.
 */
class ${module}_Controller_${type} extends Zikula_Controller
{
    /**
     * Entrypoint of ${type} controller.
     *
     * @return string HTML string
     */
    public function main()
    {
        // TODO: your controller code here

        return $view->fetch('${module}_${type}_main.tpl');
    }
}