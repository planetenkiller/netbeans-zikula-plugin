<?php
/**
 * ${module}
 *
 * @license GNU/LGPLv3 (or at your option, any later version).
 * @package ${module}
 */

/**
 * ${module} version.
 */
class ${module}_Version extends Zikula_Version
{
    public function getMetaData()
    {
        $meta = array();
        $meta['version']        = '1.0.0';
        $meta['displayname']    = $this->__('${module}');
        $meta['description']    = $this->__('${module} description...');
        //! module url should be different to displayname and in lowercase without space
        $meta['url']            = $this->__('${module}');
        $meta['securityschema'] = array('${module}::' => '::');
        return $meta;
    }
}
