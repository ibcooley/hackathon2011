<?php

namespace Simplevent\Controllers;

/**
 * site.php
 *
 * This controller handlers all basic/static requests like the contact page or the index page.
 */
class Site extends \Walleye\Controller {

    /**
     * @see Walleye_controller
     * @param array $url
     * @param array $data
     */
    public function __construct($url, $data) {
        $this->url = $url;
        $this->data = $data;
        $this->handlers = array(
            '/^(\/contact)$/' => 'contactHandler',
            '/^(\/)$/' => 'indexHandler',
            'default' => 'error_404'
        );
    }

    protected function indexHandler() {
        $this->view('index.php');
    }

    protected function contactHandler() {
        $this->view('contact.php');
    }

    protected function error_404($view = '404.php', $values = array()) {
        \Walleye\Console::alert('How did you get here?');
        parent::error_404();
    }
}

/* End of file */
