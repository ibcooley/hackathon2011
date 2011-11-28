<?php
if (!defined('SECURITY')){
	include_once(HOME_DIR.DIRECTORY_SEPARATOR.'includes'.DIRECTORY_SEPARATOR.'functions.php');
	error(403, __FILE__, __LINE__);
}

$page->addMeta('js', '/js/form.js');
$page->addMeta('js', '/js/register.js');

$page->setTitle('Register');

$page->getHeader();
?>
	<section id="register">
		<form id="registerForm" action="/api/register" method="post">
			<p>
				<label for="account_type">Account type:</label><br />
				<input type="radio" name="account_type" value="user" />User<br />
				<input type="radio" name="account_type" value="dj" />DJ
			</p>
			<p>
				<label for="username">Username:</label><br />
				<input type="text" id="username" name="username" placeholder="Username" />
			</p>
			<p>
				<label for="email">E-mail:</label><br />
				<input type="text" id="email" name="email" placeholder="example@example.com" /><br />
				<br />
				<label for="email_confirm">Confirm E-Mail:</label><br />
				<input type="text" id="email_confirm" name="email_confirm" placeholder="example@example.com" />
			</p>
			<p>
				<label for="password">Password:</label><br />
				<input type="text" id="password" name="password" placeholder="password" /><br />
				<br />
				<label for="password_confirm">Confirm Password:</label><br />
				<input type="text" id="password_confirm" name="password_confirm" placeholder="password">
			</p>
			<input type="submit" value="Register" id="registerSubmit" name="registerSubmit" />
		</form>
	</section>
<?php
$page->getFooter();
?>