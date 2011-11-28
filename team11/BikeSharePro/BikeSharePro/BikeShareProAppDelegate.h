//
//  BikeShareProAppDelegate.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BikeShareProAppDelegate : NSObject <UIApplicationDelegate>
{
    NSArray *bikeArray;
    NSDictionary *currentBike;
}

- (NSDictionary *) bikeWithSerialNumber:(NSString *) serial;

@property (nonatomic, retain) NSArray *bikeArray;

@property (nonatomic, retain) IBOutlet UIWindow *window;

@property (nonatomic, retain) IBOutlet UINavigationController *navigationController;

@property (nonatomic, retain) NSDictionary *currentBike;

@end
