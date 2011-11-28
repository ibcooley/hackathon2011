//
//  ReturnViewController.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>

@interface ReturnViewController : UIViewController <CLLocationManagerDelegate>
{
    NSDictionary *bikeDict;
    IBOutlet UILabel* timeandlocationLabel;

	CLLocation			*_location;
	CLLocationManager	*_locationManager;
}

- (IBAction)setTimeAndLocationButtonHit:(id)sender;

- (IBAction)processReturnButtonHit:(id)sender;
- (IBAction)reportButtonHit:(id)sender;

@property (nonatomic, retain) NSDictionary *bikeDict;
@property (nonatomic, retain) CLLocation *location;
@property (nonatomic, retain) CLLocationManager *locationManager;

@end
