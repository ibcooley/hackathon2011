//
//  SettingsViewController.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SettingsViewController : UIViewController
{
    IBOutlet UITextField *email;
    IBOutlet UITextField *password;
}
- (IBAction) doneButtonHit:(id) sender;
- (IBAction) noAccountButtonHit:(id) sender;

@end

