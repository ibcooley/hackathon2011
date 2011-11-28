//
//  TaskViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "TaskViewController.h"

#import "Task.h"
#import "User.h"

@implementation TaskViewController

@synthesize TaskAddUsersTableView = ivTaskAddUsersTableView;
@synthesize TaskDueDatePickerOutlet = ivTaskDueDatePickerOutlet;
@synthesize TaskStatusPickerView = ivTaskStatusPickerView;
@synthesize EditSegmentControl;
@synthesize TaskTextView = ivTaskTextView;
@synthesize ThisStory = ivThisStory;
@synthesize ThisTask = ivThisTask;
@synthesize Users = ivUsers;

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
    NSMutableArray *array = [[NSMutableArray alloc] init];
    [self setUsers:array];
    [array release];
    if ([self ThisTask]) {
        [self setTitle:@"Edit Task"];
        [[self TaskTextView] setText:[[self ThisTask] Description]];
        for (int i = 0; i < [self pickerView:[self TaskStatusPickerView] numberOfRowsInComponent:0]; i++) {
            NSString *status = [self pickerView:[self TaskStatusPickerView] titleForRow:i forComponent:0];
            if ([[[self ThisTask] Status] isEqualToString:status]) {
                [[self TaskStatusPickerView] selectRow:i inComponent:0 animated:NO];
            }
        }
    } else {
        [self setTitle:@"Add Task"];
    }
}

- (void)viewDidUnload
{
    [self setTaskTextView:nil];
    [self setTaskDueDatePickerOutlet:nil];
    [self setTaskStatusPickerView:nil];
    [self setEditSegmentControl:nil];
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
    [ivTaskTextView release];
    [ivTaskDueDatePickerOutlet release];
    [ivTaskStatusPickerView release];
    [EditSegmentControl release];
    [super dealloc];
}
- (IBAction)ChangeStatusButtonClick:(id)sender {
    [[self TaskStatusPickerView]  setHidden:NO]; 
    
}

- (IBAction)ChangeDueDateButtonClick:(id)sender {
    [[self TaskDueDatePickerOutlet] setHidden:NO];
}

- (IBAction)AssignUserButtonClick:(id)sender {
}
- (IBAction)TaskDueDatePickerChange:(id)sender {
    
}

- (IBAction)EditSegmentControlValueChanged:(id)sender 
{
    switch ([[self EditSegmentControl] selectedSegmentIndex]) {
        case 0:
            // Status
        {
            [[self TaskStatusPickerView] setHidden:NO];
            [[self TaskDueDatePickerOutlet] setHidden:YES];
            [[self TaskAddUsersTableView] setHidden:YES];
        }
            break;
        case 1:
            // Due
        {
            [[self TaskStatusPickerView] setHidden:YES];
            [[self TaskDueDatePickerOutlet] setHidden:NO];
            [[self TaskAddUsersTableView] setHidden:YES];
        }
            break;
        case 2:
            // Users
        {
            [[self TaskStatusPickerView] setHidden:YES];
            [[self TaskDueDatePickerOutlet] setHidden:YES];
            [[self TaskAddUsersTableView] setHidden:NO];
        }
            break;
        default:
            break;
    }
}

#pragma mark UITableView Data Source 
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self Users] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell"];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"Cell"];
    }
    // Set up the cell
    User *thisUser = [[self Users] objectAtIndex:[indexPath row]];
    [[cell textLabel] setText:[NSString stringWithFormat:@"%@ %@", [thisUser FirstName], [thisUser LastName]]];
    return cell;
}

#pragma mark UITableView Delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    UITableViewCell *cell = [self tableView:tableView cellForRowAtIndexPath:indexPath];
    if ([cell accessoryType] == UITableViewCellAccessoryNone) {
        [cell setAccessoryType:UITableViewCellAccessoryCheckmark];
    } else {
        [cell setAccessoryType:UITableViewCellAccessoryNone];
    }
}

#pragma mark UIPickerView DataSource
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 1;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    return 4;
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    switch (row) {
        case 0:
            return @"New";
            break;
        case 1:
            return @"Assigned";
            break;
        case 2:
            return @"In Progress";
            break;
        case 3:
            return @"Done";
            break;
        default:
            return @"";
            break;
    }
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    NSString *selectedStatus = [self pickerView:pickerView titleForRow:row forComponent:component];
    [[self ThisTask] setStatus:selectedStatus];
}

- (IBAction)DateChanged:(id)sender
{
    [[self ThisTask] setDeadline:[[self TaskDueDatePickerOutlet] date]];
}

@end
