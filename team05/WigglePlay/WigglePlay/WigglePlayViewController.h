//
//  WigglePlayViewController.h
//  WigglePlay
//
//  Created by Michael Vaughan on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WigglePlayViewController : UIViewController {
    
}

@property (nonatomic, retain) IBOutlet UIWebView *webView;
@property (nonatomic, retain) IBOutlet UIImageView *imageView01;
@property (nonatomic, retain) IBOutlet UIImageView *imageView02;
@property (nonatomic, retain) IBOutlet UIImageView *imageView03;
@property (nonatomic, retain) IBOutlet UIImageView *imageView04;
@property (nonatomic, retain) IBOutlet UIImageView *imageView05;
@property (nonatomic, retain) IBOutlet UIImageView *backgroundView;

@property (nonatomic, retain) IBOutlet UIImage *image01;
@property (nonatomic, retain) IBOutlet UIImage *image02;
@property (nonatomic, retain) IBOutlet UIImage *image03;
@property (nonatomic, retain) IBOutlet UIImage *image04;
@property (nonatomic, retain) IBOutlet UIImage *image05;
@property (nonatomic, retain) IBOutlet UIImage *background;

@property (nonatomic, retain) IBOutlet UIButton *button;


- (IBAction)playButtonPressed;
- (IBAction)wiggleButtonPressed;
- (IBAction)backButtonPressed;
- (void) rotateXTimes:(int) timesToRotate; 

@end
