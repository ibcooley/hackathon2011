//
//  WigglePlayViewController.m
//  WigglePlay
//
//  Created by Michael Vaughan on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <AudioToolbox/AudioServices.h>
#import <AVFoundation/AVFoundation.h>
#import "WigglePlayViewController.h"

@implementation WigglePlayViewController

@synthesize webView;
@synthesize imageView01;
@synthesize imageView02;
@synthesize imageView03;
@synthesize imageView04;
@synthesize imageView05;
@synthesize backgroundView;

@synthesize image01;
@synthesize image02;
@synthesize image03;
@synthesize image04;
@synthesize image05;
@synthesize background;

@synthesize button;

UIImageView *currentlySelected;
bool continueLoop = true;



- (void)dealloc
{
    [super dealloc];
}

- (AVAudioPlayer *) soundNamed:(NSString *)name {
    NSString * path;
    AVAudioPlayer * snd;
    NSError * err;
    
    path = [[[NSBundle mainBundle] resourcePath] stringByAppendingPathComponent:name];
    
    if ([[NSFileManager defaultManager] fileExistsAtPath:path]) {
        NSURL * url = [NSURL fileURLWithPath:path];
        snd = [[[AVAudioPlayer alloc] initWithContentsOfURL:url 
                                                      error:&err] autorelease];
        if (! snd) {
            NSLog(@"Sound named '%@' had error %@", name, [err localizedDescription]);
        } else {
            [snd prepareToPlay];
        }
    } else {
        NSLog(@"Sound file '%@' doesn't exist at '%@'", name, path);
    }
    
    return snd;
}


- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

-(void) rotateImages:(UIImageView*) spotOne spotTwo:(UIImageView*) spotTwo spotThree:(UIImageView*) spotThree spotFour:(UIImageView*) spotFour spotFive:(UIImageView *) spotFive {
    
    [UIView transitionWithView:spotOne.superview duration:0.2 options:UIViewAnimationTransitionFlipFromRight animations:^{ [spotOne setFrame:CGRectMake( 209.0, 259.0, 350, 300)];  } completion:nil];
    [UIView transitionWithView:spotOne.superview duration:0.2 options:UIViewAnimationTransitionFlipFromRight animations:^{ [spotTwo setFrame:CGRectMake( 5.0, 127.0, 350, 300)];  } completion:nil];
    [UIView transitionWithView:spotOne.superview duration:0.2 options:UIViewAnimationTransitionFlipFromRight animations:^{ [spotThree setFrame:CGRectMake( 28.0, 20.0, 350, 300)];  } completion:nil];
    [UIView transitionWithView:spotOne.superview duration:0.2 options:UIViewAnimationTransitionFlipFromRight animations:^{ [spotFour setFrame:CGRectMake( 386.0, 20.0, 350, 300)];  } completion:nil];
    [UIView transitionWithView:spotOne.superview duration:0.2 options:UIViewAnimationTransitionFlipFromRight animations:^{ [spotFive setFrame:CGRectMake( 413.0, 127.0, 350, 300)];  } completion:nil];
    
    [spotOne.superview bringSubviewToFront:spotFour];
    [spotOne.superview bringSubviewToFront:spotThree];
    [spotOne.superview bringSubviewToFront:spotTwo];
    [spotOne.superview bringSubviewToFront:spotFive];
    [spotOne.superview bringSubviewToFront:spotOne];
    
    currentlySelected = spotOne;
    
}

//function to detect the shake motion
- (void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event {
    
    if (event.type == UIEventSubtypeMotionShake) {
        NSLog(@"in the shake");
        
        [[self soundNamed:@"asYouGroove.mp3"] play];
        sleep(1);
        NSInteger x = 5;
        while (  ( x =  (arc4random() % 8) + 25 ) % 5 == 0 ) {
            
        }
        NSLog(@"%i", x);     
        [self rotateXTimes:x];
    }
}

//needed for SHake gesture
- (BOOL)canBecomeFirstResponder {
    return YES;
}


- (void) rotateXTimes:(int) timesToRotate {
    for ( int i = 0; i < timesToRotate; i++ ) {
        
        if ( currentlySelected == imageView01 ) {
            
            [self rotateImages:imageView05 
                       spotTwo:imageView01
                     spotThree: imageView02
                      spotFour:imageView03
                      spotFive:imageView04];
            
            
        } else if ( currentlySelected == imageView02 ) {
            
            [self rotateImages:imageView01 
                       spotTwo:imageView02
                     spotThree: imageView03
                      spotFour:imageView04
                      spotFive:imageView05];
            
        } else if ( currentlySelected == imageView03 ) {
            
            [self rotateImages:imageView02 
                       spotTwo:imageView03
                     spotThree: imageView04
                      spotFour:imageView05
                      spotFive:imageView01];
            
        } else if ( currentlySelected == imageView04 ) {
            
            [self rotateImages:imageView03 
                       spotTwo:imageView04
                     spotThree: imageView05
                      spotFour:imageView01
                      spotFive:imageView02];
            
        } else {
            
            [self rotateImages:imageView04 
                       spotTwo:imageView05
                     spotThree: imageView01
                      spotFour:imageView02
                      spotFive:imageView03];
            
        }
        
    }
    
}


- (void)viewDidAppear:(BOOL)animated {
    [self becomeFirstResponder];
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [button.superview sendSubviewToBack:button];
    
    image01 = [UIImage imageNamed:@"concentration.png"];
    image02 = [UIImage imageNamed:@"bubbleTrouble.png"];
    image03 = [UIImage imageNamed:@"mineSweeper.png"];
    image04 = [UIImage imageNamed:@"bunnyHunt.png"];
    image05 = [UIImage imageNamed:@"stack.png"];
    background = [UIImage imageNamed:@"backGround.png"];

    [imageView01 setImage:image01];
    [imageView02 setImage:image02];
    [imageView03 setImage:image03];
    [imageView04 setImage:image04];
    [imageView05 setImage:image05];
    [backgroundView setImage:background];

    currentlySelected = imageView01;
    

    /* squareImage = [UIImage imageWithCGImage: squareImage.CGImage scale:.50 orientation:UIImageOrientationUp];
    [imageView setImage:[UIImage imageNamed:@"square.png"]];
    
    // Setup the animation
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationCurve:UIViewAnimationCurveLinear];
    [UIView setAnimationBeginsFromCurrentState:YES];
    
    imageView.frame = CGRectMake(0, 0, 30, 30); 
    // The transform matrix
    CGAffineTransform transform = CGAffineTransformMakeTranslation(50.0, 50.0);
    
    imageView.transform = transform;
    
    // Commit the changes
    [UIView commitAnimations];
 */
    
    //[self startApp];
}

//Function for pressing the play button
- (IBAction)playButtonPressed
{
    NSLog(@"play with me");
    NSString *urlAddress;
    
    if ( currentlySelected == imageView01 ) {
        
        urlAddress = @"http://javascript.internet.com/games/concentration.html";
        
        
    } else if ( currentlySelected == imageView02 ) {
        
        urlAddress = @"http://xwuz.com/bubble/";
        
    } else if ( currentlySelected == imageView03 ) {
        //minesweeper
        urlAddress = @"http://www.chezpoor.com/minesweeper/minecore.html";
        
        
    } else if ( currentlySelected == imageView04 ) {
        
        urlAddress = @"http://www.themaninblue.com/experiment/BunnyHunt/";
        
    } else {
        
        urlAddress = @"http://xwuz.com/stack/";
        
    }
    
    NSURL *url = [NSURL URLWithString:urlAddress];
     NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
     
    [webView.superview bringSubviewToFront:webView]; 
     [webView loadRequest:requestObj];
    
    [button.superview bringSubviewToFront:button];
     
}


- (IBAction) wiggleButtonPressed
{
    NSLog(@"wiggle me");
    [[self soundNamed:@"asYouGroove.mp3"] play];
    sleep(1);
    NSInteger x = 5;
    while (  ( x =  (arc4random() % 8) + 25 ) % 5 == 0 ) {
        
    }
    NSLog(@"%i", x);     
    [self rotateXTimes:x];


}

- (IBAction)backButtonPressed
{
    //back button pressed
    [webView.superview sendSubviewToBack:webView];
    [button.superview sendSubviewToBack:button];
}



- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return YES;
}

@end
