//
//  StoryViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "StoryEditViewController.h"

#import "Story.h"

@implementation StoryEditViewController

@synthesize StoryTitleField = ivStoryTitleField;
@synthesize StoryTextView = ivStoryTextView;
@synthesize StoryStatusPicker = ivStoryStatusPicker;
@synthesize ThisStory = ivThisStory;

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
    [[self StoryTitleField] setText:[[self ThisStory] Title]];
    [[self StoryTextView] setText:[[self ThisStory] Description]];
}

- (void)viewDidUnload
{
    [self setStoryTextView:nil];
    [self setStoryTitleField:nil];
    [self setStoryStatusPicker:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)StoryTitleFieldEndEdit:(id)sender {
}

- (IBAction)EditButton:(id)sender {
}

- (IBAction)StatusChangeButton:(id)sender {
}
- (void)dealloc {
    [ivStoryTextView release];
    [ivStoryTitleField release];
    [ivStoryStatusPicker release];
    [super dealloc];
}
@end
