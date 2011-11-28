<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

define('SECURITY', TRUE);

define('HOME_DIR',       dirname(__FILE__).DIRECTORY_SEPARATOR);
define('INCLUDES',       HOME_DIR.'includes'.DIRECTORY_SEPARATOR);
define('INCLUDES_PAGES', INCLUDES.DIRECTORY_SEPARATOR.'pages'.DIRECTORY_SEPARATOR);

define('URL', 'http://sparc.develogix.com/');

define('TIMEOUT',       600);
define('SALT',          md5('_SPARC_SALT_'));
define('SALT_PASSWORD', md5('_SALT_'.SALT.'_SALT_'));
define('DEBUG',         TRUE);

include_once(INCLUDES.'functions.php');
include_once(INCLUDES.'db.class.php');
include_once(INCLUDES.'secure.session.class.php');
include_once(INCLUDES.'visitor.class.php');
include_once(INCLUDES.'page.class.php');
include_once(INCLUDES.'settings.class.php');

$db       = new DB();
$session  = new SecureSession(TIMEOUT, SALT);
$visitor  = ((boolval($session->get('logged_in')) === TRUE) AND ($session->get('hash') !== NULL)) ? new Visitor($session->get('hash')) : NULL;
$page     = new Page();
$settings = new Settings(1);

echo $page;
?>