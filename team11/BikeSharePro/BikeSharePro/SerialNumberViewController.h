//
//  SerialNumberViewController.h
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SerialNumberViewController : UIViewController <UIImagePickerControllerDelegate>
{
    NSDictionary *bikeDict;
    IBOutlet UITextField *serialNumberField;
    IBOutlet UIImageView *imageView;
}

- (IBAction)doneButtonHit:(id)sender;
- (IBAction) pictureButtonHit:(id) sender;

@property (nonatomic, retain) NSDictionary *bikeDict;

@end
