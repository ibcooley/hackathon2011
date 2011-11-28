//
//  StoryViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "StoryViewController.h"

@implementation StoryViewController
@synthesize StoryTitleField;
@synthesize StoryTextView;
@synthesize StoryStatusPicker;

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
    [StoryTextView release];
    [StoryTitleField release];
    [StoryStatusPicker release];
    [super dealloc];
}
@end
