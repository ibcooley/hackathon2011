<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}

if(($page->getQueryCount() === 1) && (strtolower($page->getQuery(0)) === 'login')){
	$page->addMeta('js', '/js/form.js');
	$page->addMeta('js', '/js/login.js');

	$page->setTitle('Login');
	$page->header();
?>
	<section id="register">
		<a href="/register">Need to register?</a>
	</section>
	<section id="login">
		<form id="loginForm" action="/api/login" method="post">
			<input type="text" placeholder="username" id="username" name="username" /><br />
			<input type="password" placeholder="password" id="password" name="password"><br />
			<input type="submit" value="Login" id="submit" name="submit">
		</form>
	</section>
<?php
	$page->footer();
}
else error(404, __FILE__, __LINE__);
?>