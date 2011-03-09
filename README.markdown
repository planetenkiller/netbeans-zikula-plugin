Zikula Framework support for Netbeans PHP
=========================================

Installation
------------
- via plugin center (auto update center) (recomanded)
    - to to the plugin center (tools -> plugins), switch to tab settings, click add and use this url: "http://timeit.roland-web.net/zikulaNetbeansPlugin/updates.xml" (without ")

- Download the .nbm file form the downloads section (button above) and install it via plugin center

- checkout the source and build the plugin yourself.

Required structure of a php project
-----------------------------------
Netbeans will activate zikula support on a php project if the project has the following structure:
<pre>
- PHP Project of your zikula module
   \- src
       \- modules
           \- YourModule
              \- lib
                 \- YourModule
                     |- Installer.php
                     \- Version.php
</pre>

Features
--------
 - displays a folder under project for: controllers, apis, models and templates
 - new entries for New -> File

Comming features
----------------
 - go to view (from action)
 - go to action (from view)
