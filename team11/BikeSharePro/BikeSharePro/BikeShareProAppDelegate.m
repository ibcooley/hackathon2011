//
//  BikeShareProAppDelegate.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "BikeShareProAppDelegate.h"

@implementation BikeShareProAppDelegate

@synthesize window = _window;
@synthesize navigationController = _navigationController;

@synthesize bikeArray;
@synthesize currentBike;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    // Add the navigation controller's view to the window and display.
    
    self.window.rootViewController = self.navigationController;

	UIImage* backImage = [UIImage imageNamed:@"Default.png"];
	UIView* backView = [[UIImageView alloc] initWithImage:backImage];
	
	backView.frame = self.window.bounds;
	
	[self.window addSubview:backView];
	[UIView beginAnimations:@"CWFadeIn" context:(void*)backView];
	[UIView setAnimationDelegate:self];
	[UIView setAnimationDidStopSelector:@selector(animationDidStop:finished:context:)];
	[UIView setAnimationDuration:0.75f];
	backView.alpha = 0;
	[UIView commitAnimations];	
 

    [self.window makeKeyAndVisible];
    
    NSString *path = [[NSBundle mainBundle] pathForResource:
                      @"bikes" ofType:@"plist"];
    NSDictionary *plistDict = [NSDictionary dictionaryWithContentsOfFile:path];
    self.bikeArray = [plistDict objectForKey:@"bikes"];
    
    self.currentBike = [[NSUserDefaults standardUserDefaults] objectForKey:@"currentBike"];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

- (void)dealloc
{
    [_window release];
    [_navigationController release];
    [bikeArray release];
    [currentBike release];
    [super dealloc];
}

- (NSDictionary *) bikeWithSerialNumber:(NSString *) serial
{
    for (NSDictionary *bike in bikeArray)
    {
        if ([[bike objectForKey:@"serial"] isEqualToString:serial])
            return bike;
    }
    return nil;
}

@end
