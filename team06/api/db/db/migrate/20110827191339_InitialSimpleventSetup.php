<?php

class InitialSimpleventSetup extends Ruckusing_BaseMigration
{

    public function up()
    {
        // Events
        $events = $this->create_table('Events');

        $events->column('title', 'string');
        $events->column('desc', 'string');
        $events->column('is_private', 'boolean', array('default' => 0));
        $events->column('longitude', 'string');
        $events->column('latitude', 'string');
        $events->column('is_over', 'boolean', array('default' => 0));
        $events->column('is_busted', 'boolean', array('default' => 0));
        $events->column('date_created', 'timestamp', array('default' => 'CURRENT_TIMESTAMP'));

        $events->finish();

        // EventUsers
        $event_users = $this->create_table('EventUsers');
        $event_users->column('event_id', 'integer');
        $event_users->column('user_id', 'integer');
        $event_users->column('user_group', 'integer', array('default' => 0));
        $event_users->column('date_created', 'timestamp');

        $event_users->finish();
        
        // Items
        $items = $this->create_table('Items');
        $items->column('title', 'string');
        $items->column('desc', 'string');
        $items->column('created_by_user_id', 'integer');
        $items->column('claimed_by_user_id', 'integer', array('default' => 0));
        $items->column('event_id', 'integer');
        $items->column('date_created', 'timestamp', array('default' => 'CURRENT_TIMESTAMP'));

        $items->finish();

        // UserUsers
        $user_users = $this->create_table('UserUsers');
        $user_users->column('requested_by_user_id', 'integer');
        $user_users->column('recipient_user_id', 'integer');
        $user_users->column('is_pending', 'boolean', array('default' => 1));
        $user_users->column('date_created', 'timestamp', array('default' => 'CURRENT_TIMESTAMP'));

        $user_users->finish();

        // Comments
        $comments = $this->create_table('Comments');
        $comments->column('event_id', 'integer');
        $comments->column('created_by_user_id', 'integer');
        $comments->column('comment', 'string');
        $comments->column('date_created', 'timestamp', array('default' => 'CURRENT_TIMESTAMP'));

        $comments->finish();

        // add display name field to Users
        $this->add_column('Users', 'display_name', 'string');

    }

    //up()

    public function down()
    {
        $this->drop_table('Events');
        $this->drop_table('EventUsers');
        $this->drop_table('Items');
        $this->drop_table('UserUsers');
        $this->drop_table('Comments');

        $this->remove_column('Users', 'display_name');
    }
    //down()
}

?>
