<?php
/**
 * ${module}
 *
 * @license GNU/LGPLv3 (or at your option, any later version).
 * @package ${module}
 */

/**
 * ${module} installer.
 */
class ${module}_Installer extends Zikula_Installer
{
    /**
     * Installation.
     *
     * @return boolean
     */
    public function install()
    {
        // TODO: implement install code

        return true;
    }

    /**
     * Upgrade.
     *
     * @param string $oldVersion Old version.
     *
     * @return boolean
     */
    public function upgrade($oldVersion)
    {
        /* TODO: implement upgrade code

        // Upgrade dependent on old version number
        switch ($oldVersion) {
            case '0.0.0':
                // upgrade from 0.0.0 to 1.0.0
            case '1.0.0':
                // upgrade from 1.0.0 to 1.1.0
            case '1.1.0':
                // upgrade from 1.1.0 to x.x.x
        }
        */

        // Update successful
        return true;
    }

    /**
     * Uninstallation.
     *
     * @return boolean
     */
    public function uninstall()
    {
        // TODO: implement uninstall code

        // Deletion successful
        return true;
    }
}