//
//  TaskViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "TaskViewController.h"

@implementation TaskViewController
@synthesize TaskStatusPickerView;
@synthesize TaskDueDatePickerOutlet;
@synthesize TaskTextView;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [self setTaskTextView:nil];
    [self setTaskDueDatePickerOutlet:nil];
    [self setTaskStatusPickerView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)dealloc {
    [TaskTextView release];
    [TaskDueDatePickerOutlet release];
    [TaskStatusPickerView release];
    [super dealloc];
}
- (IBAction)ChangeStatusButtonClick:(id)sender {
    [TaskStatusPickerView  setHidden:NO]; 
    
}

- (IBAction)ChangeDueDateButtonClick:(id)sender {
    [TaskDueDatePickerOutlet setHidden:NO];
}

- (IBAction)AssignUserButtonClick:(id)sender {
}
- (IBAction)TaskDueDatePickerChange:(id)sender {
    
}
@end
