<?php

namespace Simplevent\Models;

/**
 * A Person can be created directly. If done so, it represents someone who is
 * not registered
 */
class Person extends \Walleye\User
{
    /**
     * @var string
     */
    public $display_name;

    /**
     * Nicknames to be given to guests at random
     *
     * @var array
     */
    public $guest_nicks = array(
        'Fred Flinstone',
        'Wilma Flinstone',
        'Barney the Dino'
    );

    const ID_OF_UNKNOWN_USER = 0;

    const BLANK_DISPLAY_NAME = '';

    /**
     * @param int $id
     */
    public function __construct($id = Person::ID_OF_UNKNOWN_USER)
    {
        if ($id != Person::ID_OF_UNKNOWN_USER && $this->display_name == Person::BLANK_DISPLAY_NAME) {
            // set a random display name if none given
            $this->display_name = $this->guest_nicks[rand(0, 3)];
        }

        if ($id != Person::ID_OF_UNKNOWN_USER) {
            parent::__construct($id);
        }
    }

    /**
     * @return string
     */
    public function getDisplayName()
    {
        return $this->display_name;
    }

}
 
/* End of file */
