//
//  RootViewController.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "RootViewController.h"
#import "SettingsViewController.h"
#import "CheckOutViewController.h"
#import "ReturnViewController.h"
#import "ReportViewController.h"

@implementation RootViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"Bike Share Pro";
    self.navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;

    self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc]initWithTitle:NSLocalizedString(@"Settings", @"Settings") style:UIBarButtonItemStyleBordered target:self action:@selector(settingsButtonHit:)] autorelease];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];

    groupView.hidden = YES;
    if (0)
    {
        SettingsViewController *settingsView = [[[SettingsViewController alloc] init] autorelease];
        if (settingsView)
        {
            UINavigationController *navigationController = [[[UINavigationController alloc] initWithRootViewController:settingsView] autorelease];
            navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
            [self presentModalViewController:navigationController animated:YES];
        }
    }

    BikeShareProAppDelegate *delegate = [[UIApplication sharedApplication] delegate];
    if (delegate.currentBike)
    {
        imageView.hidden = YES;
        
        [checkoutReturnButton setTitle:@"Return Your Bike" forState:UIControlStateNormal];
        [checkoutReturnButton setTitle:@"Return Your Bike" forState:UIControlStateHighlighted];
        [checkoutReturnButton setTitle:@"Return Your Bike" forState:UIControlStateDisabled];
        [checkoutReturnButton setTitle:@"Return Your Bike" forState:UIControlStateSelected];
        
        groupView.hidden = NO;
        name.text = [delegate.currentBike objectForKey:@"name"];
        date.text = [NSString stringWithFormat:@"At: %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"currentDate"]];
        location.text = [NSString stringWithFormat:@"From:\n%@", [[NSUserDefaults standardUserDefaults] objectForKey:@"currentLocation"]];
        combination.text = [NSString stringWithFormat:@"Combination: %@", [delegate.currentBike objectForKey:@"combination"]];
     }
    else
    {
        imageView.hidden = NO;
        groupView.hidden = YES;
        
        [checkoutReturnButton setTitle:@"Check Out a Bike" forState:UIControlStateNormal];
        [checkoutReturnButton setTitle:@"Check Out a Bike" forState:UIControlStateHighlighted];
        [checkoutReturnButton setTitle:@"Check Out a Bike" forState:UIControlStateDisabled];
        [checkoutReturnButton setTitle:@"Check Out a Bike" forState:UIControlStateSelected];
    }
    
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}


- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Relinquish ownership any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload
{
    [super viewDidUnload];

    // Relinquish ownership of anything that can be recreated in viewDidLoad or on demand.
    // For example: self.myOutlet = nil;
}

- (void)dealloc
{
    [super dealloc];
}

- (IBAction)checkOutButtonHit:(id)sender;
{
    BikeShareProAppDelegate *delegate = [[UIApplication sharedApplication] delegate];
    if (!delegate.currentBike)
    {
        CheckOutViewController *checkoutViewController = [[[CheckOutViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
        if (checkoutViewController)
        {
            checkoutViewController.navigationItem.hidesBackButton = NO;

            [self.navigationController pushViewController:checkoutViewController animated:YES];
//            UINavigationController *navigationController = [[[UINavigationController alloc] initWithRootViewController:checkoutViewController] autorelease];
//            navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
//            [self presentModalViewController:navigationController animated:YES];
        }   
    }
    else
    {
        ReturnViewController *returnViewController = [[[ReturnViewController alloc] init] autorelease];
        if (returnViewController)
        {
            UINavigationController *navigationController = [[[UINavigationController alloc] initWithRootViewController:returnViewController] autorelease];
            navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
            [self presentModalViewController:navigationController animated:YES];
        }   

    }
}

- (IBAction)contactButtonHit:(id)sender;
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Call Bike Share?" message:@"We'd love to hear from you!" delegate:self cancelButtonTitle:@"No" otherButtonTitles:@"Yes", nil];
	[alertView show];
	[alertView release];
}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex;  // after animation
{
    if (buttonIndex != 0)
    {
        NSString *phoneNumber = @"(843) 867-5309";
        NSString *cleanedString = [[phoneNumber componentsSeparatedByCharactersInSet:[[NSCharacterSet characterSetWithCharactersInString:@"0123456789-+()"] invertedSet]] componentsJoinedByString:@""];
        NSString *escapedPhoneNumber = [cleanedString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        NSURL *telURL = [NSURL URLWithString:[NSString stringWithFormat:@"tel://%@", escapedPhoneNumber]];

        [[UIApplication sharedApplication] openURL:telURL];    
    }
}

- (IBAction)settingsButtonHit:(id)sender;
{
    SettingsViewController *settingsView = [[[SettingsViewController alloc] init] autorelease];
    if (settingsView)
    {
        UINavigationController *navigationController = [[[UINavigationController alloc] initWithRootViewController:settingsView] autorelease];
        navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
        [self presentModalViewController:navigationController animated:YES];
    }    
}

- (IBAction)reportButtonHit:(id)sender;
{
    ReportViewController *reportView = [[[ReportViewController alloc] init] autorelease];
    if (reportView)
    {
        UINavigationController *navigationController = [[[UINavigationController alloc] initWithRootViewController:reportView] autorelease];
        navigationController.navigationBar.barStyle = UIBarStyleBlackOpaque;
        [self presentModalViewController:navigationController animated:YES];
    }    
}
@end
