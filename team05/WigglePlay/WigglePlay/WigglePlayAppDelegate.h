//
//  WigglePlayAppDelegate.h
//  WigglePlay
//
//  Created by Michael Vaughan on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class WigglePlayViewController;

@interface WigglePlayAppDelegate : NSObject <UIApplicationDelegate> {

}

@property (nonatomic, retain) IBOutlet UIWindow *window;

@property (nonatomic, retain) IBOutlet WigglePlayViewController *viewController;

@end
