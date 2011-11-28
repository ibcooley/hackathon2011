//
//  RootViewController.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RootViewController : UIViewController
{
    IBOutlet UIView *groupView;
    IBOutlet UILabel *name;
    IBOutlet UILabel *date;
    IBOutlet UILabel *location;
    IBOutlet UILabel *combination;
    IBOutlet UIButton *checkoutReturnButton;
    IBOutlet UIImageView *imageView;
}

- (IBAction)checkOutButtonHit:(id)sender;
- (IBAction)contactButtonHit:(id)sender;
- (IBAction)settingsButtonHit:(id)sender;
- (IBAction)reportButtonHit:(id)sender;
@end
