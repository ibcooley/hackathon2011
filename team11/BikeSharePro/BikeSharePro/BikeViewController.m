//
//  BikeViewController.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BikeViewController.h"
#import "SerialNumberViewController.h"
#import "BikeShareProAppDelegate.h"

@implementation BikeViewController

@synthesize bikeDict = bikeDict;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"Bike Info";
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
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void) viewWillAppear:(BOOL) animated
{
    [super viewWillAppear:animated];
    
    bikeName.text = [bikeDict objectForKey:@"name"];
    bikeCost.text = [NSString stringWithFormat:@"$%@ per hour", [bikeDict objectForKey:@"cost"]];
    bikeSerial.text = [bikeDict objectForKey:@"serial"];
    
    NSString *imageName = [bikeDict objectForKey:@"image"];
    
    NSString *path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:imageName];
    bikeImage.image = [UIImage imageWithContentsOfFile:path];    
}

- (void) viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void) dealloc
{
    [bikeDict release];
    [super dealloc];
}

- (IBAction)checkOutButtonHit:(id)sender;
{
    // Pass the selected object to the new view controller.
    SerialNumberViewController *serialController = [[SerialNumberViewController alloc] init];
    [self.navigationController pushViewController:serialController animated:YES];
    [serialController release];
}


@end
