//
//  ProjectViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "ProjectViewController.h"

#import "Project.h"

@implementation ProjectViewController

@synthesize ProjectTitleTextField = ivProjectTitleTextField;
@synthesize ThisProject = ivThisProject;
@synthesize TitleButton = ivTitleButton;

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

- (void)viewWillAppear:(BOOL)animated
{
    [super viewDidLoad];
    if ([self ThisProject]) {
        [self setTitle:@"Edit Project Title"];
        [[self ProjectTitleTextField] setPlaceholder:@"Edit Project Name"];
        [[self ProjectTitleTextField] setText:[[self ThisProject] Title]];
        [[self TitleButton] setTitle:@"Edit Project Title" forState:UIControlStateNormal];
    } else {
        [self setTitle:@"Add Project"];
    }
}

- (void)viewDidUnload
{
    [self setProjectTitleTextField:nil];
    [self setTitleButton:nil];
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
    [ivProjectTitleTextField release];
    [ivThisProject release];
    [ivTitleButton release];
    [super dealloc];
}
- (IBAction)AddProjectButtonClick:(id)sender {
    if ([self ThisProject]) {
        [[self ThisProject] setTitle:[[self ProjectTitleTextField] text]];
    }
    [[self navigationController] popViewControllerAnimated:YES];
}
@end
