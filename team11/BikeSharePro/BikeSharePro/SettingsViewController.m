//
//  SettingsViewController.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SettingsViewController.h"
#import "WebViewController.h"

@implementation SettingsViewController

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
    
    self.title = @"Account Information";
	self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc]initWithTitle:NSLocalizedString(@"Done", @"Done") style:UIBarButtonItemStyleBordered target:self action:@selector(doneButtonHit:)] autorelease];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void) viewWillAppear:(BOOL) animated
{
    [super viewWillAppear:animated];
    
    email.text = [[NSUserDefaults standardUserDefaults] objectForKey:@"emailString"];
    password.text = [[NSUserDefaults standardUserDefaults] objectForKey:@"passwordString"];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction) doneButtonHit:(id) sender;
{
    [[NSUserDefaults standardUserDefaults] setObject:email.text forKey:@"emailString"];
    [[NSUserDefaults standardUserDefaults] setObject:password.text forKey:@"passwordString"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    [self dismissModalViewControllerAnimated:YES];
}

- (IBAction) noAccountButtonHit:(id) sender;
{
    NSString*	str = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:@"bike.html"];
    WebViewController *webView = [[[WebViewController alloc] initWithResource:str] autorelease];
    UINavigationController *navigationController = [[[UINavigationController alloc] initWithRootViewController:webView] autorelease];
    navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
    [self presentModalViewController:navigationController animated:YES];
}

@end
