//
//  ReturnViewController.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "ReturnViewController.h"
#import "BikeShareProAppDelegate.h"
#import "ReportViewController.h"

@implementation ReturnViewController

@synthesize bikeDict = bikeDict;
@synthesize location = _location;
@synthesize locationManager = _locationManager;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"Return";

        self.navigationItem.leftBarButtonItem = [[[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancelButtonHit:)] autorelease];
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
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    // Do any additional setup after loading the view from its nib.
    if (self.locationManager == nil)
        self.locationManager = [[CLLocationManager alloc] init];
    if ([CLLocationManager locationServicesEnabled]) {
        self.locationManager.delegate = self;
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
        self.locationManager.distanceFilter = 1000.0f;
        [self.locationManager startUpdatingLocation];
    }
    
    [self setTimeAndLocationButtonHit:nil];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)setTimeAndLocationButtonHit:(id)sender;
{
    NSString *loc = @"";
    NSString *dateString = @"";
    
    if (self.location)
    {
        loc = [NSString stringWithFormat:@"Lat: %g Long: %g", self.location.coordinate.latitude, self.location.coordinate.longitude];       
    }
    else
    {
        loc = @"2387 Clements Ferry Rd\nCharleston, SC 29492";
    }
    
    NSDate *today = [NSDate date];
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"MM/dd/yyyy hh:mma"];
    dateString = [dateFormat stringFromDate:today];
    [dateFormat release];
    
    timeandlocationLabel.text = [NSString stringWithFormat:@"Returned at:\n%@\n%@", dateString, loc];
}
- (IBAction)processReturnButtonHit:(id)sender;
{
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"currentBike"];
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"currentDate"];
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"currentLocation"];

    [[NSUserDefaults standardUserDefaults] synchronize];
    BikeShareProAppDelegate *delegate = [[UIApplication sharedApplication] delegate];
    delegate.currentBike = nil;
    
    [self dismissModalViewControllerAnimated:YES];
    
    UIAlertView *alert = [[[UIAlertView alloc]
                           initWithTitle:NSLocalizedString(@"Thank You!", @"Thank You!") 
                           message:@"Your return has been processed. We'll email a receipt to the email address on file." 
                           delegate:nil 
                           cancelButtonTitle: NSLocalizedString(@"OK", @"OK")
                           otherButtonTitles:nil] autorelease];
    [alert show];    
}


#pragma mark CLLocationManagerDelegate
- (void) locationManager: (CLLocationManager *) manager
	 didUpdateToLocation: (CLLocation *) newLocation
			fromLocation: (CLLocation *) oldLocation
{
	self.location = newLocation;
    [self setTimeAndLocationButtonHit:nil];
}

- (void) locationManager: (CLLocationManager *) manager
		didFailWithError: (NSError *) error 
{
	self.location = nil;
	
	NSString *msg = [[[NSString alloc] 
                      initWithString:NSLocalizedString(@"Error obtaining location", @"Error obtaining location")] autorelease];
    UIAlertView *alert = [[[UIAlertView alloc]
                           initWithTitle:NSLocalizedString(@"Error", @"Error") 
                           message:msg 
                           delegate:nil 
                           cancelButtonTitle: NSLocalizedString(@"OK", @"OK")
                           otherButtonTitles:nil] autorelease];
    [alert show];    
}

- (IBAction)cancelButtonHit:(id)sender;
{
    [self dismissModalViewControllerAnimated:YES];
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
