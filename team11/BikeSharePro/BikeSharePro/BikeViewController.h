//
//  BikeViewController.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BikeViewController : UIViewController
{
    NSDictionary *bikeDict;
    IBOutlet UILabel* bikeName;
    IBOutlet UILabel* bikeCost;
    IBOutlet UILabel* bikeSerial;
    IBOutlet UIImageView* bikeImage;
}

- (IBAction)checkOutButtonHit:(id)sender;

@property (nonatomic, retain) NSDictionary *bikeDict;

@end
