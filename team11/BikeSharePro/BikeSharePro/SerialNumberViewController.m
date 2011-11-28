//
//  SerialNumberViewController.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SerialNumberViewController.h"
#import "BikeShareProAppDelegate.h"

@implementation SerialNumberViewController

@synthesize bikeDict = bikeDict;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"Serial Number";
        self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc]initWithTitle:NSLocalizedString(@"Submit", @"Submit") style:UIBarButtonItemStyleBordered target:self action:@selector(doneButtonHit:)] autorelease];

    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void) viewWillAppear:(BOOL) animated
{
   // watch the keyboard so we can adjust the user interface if necessary.
   [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) 
                                                name:UIKeyboardWillShowNotification object:self.view.window]; 
   [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardDidShow:) 
                                                name:UIKeyboardDidShowNotification object:self.view.window]; 
   [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) 
                                                name:UIKeyboardWillHideNotification object:self.view.window]; 
   
   self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"Submit", @"Submit") 
                                                                              style:UIBarButtonItemStyleDone target:self action:@selector(doneButtonHit:)] autorelease];

}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)doneButtonHit:(id)sender;
{
	if ([serialNumberField isFirstResponder])
    {
        [serialNumberField resignFirstResponder];
        return;
    }
    
    if (serialNumberField.text && [serialNumberField.text length])
    {
       BikeShareProAppDelegate *delegate = [[UIApplication sharedApplication] delegate];
        NSDictionary *bike = [delegate bikeWithSerialNumber:serialNumberField.text];

       if (!bike)
       {
           UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Entry error!" message:@"This is not a valid serial number. Please make sure you are entering the number correctly.\n\n(hint: enter a number between 10001 and 10010)" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
           [alertView show];
           [alertView release];
       }
        else
        {
            NSString *combination = [bike objectForKey:@"combination"];
            NSString *alertString = [NSString stringWithFormat:@"You've successfully entered your bike's serial number\n\nYour combination is:\n\n%@", combination];
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Congratulations!" message:alertString delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
            [alertView show];
            [alertView release];
            delegate.currentBike = bike;
            
            
            NSDate *today = [NSDate date];
            NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
            [dateFormat setDateFormat:@"MM/dd/yyyy hh:mma"];
            NSString *dateString = [dateFormat stringFromDate:today];
            [dateFormat release];
            [[NSUserDefaults standardUserDefaults] setObject:dateString forKey:@"currentDate"];
            [[NSUserDefaults standardUserDefaults] setObject:@"2387 Clements Ferry Rd\nCharleston, SC 29492" forKey:@"currentLocation"];
            [[NSUserDefaults standardUserDefaults] setObject:bikeDict forKey:@"currentBike"];
            [[NSUserDefaults standardUserDefaults] synchronize];
            
            [self.navigationController popToRootViewControllerAnimated:YES];
       }
     }
    else
    {
        UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Entry error!" message:@"Please enter a serial number." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
        [alertView show];
        [alertView release];
    }
}

- (IBAction) pictureButtonHit:(id) sender;
{
	[serialNumberField resignFirstResponder];

    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Take a Picture!" message:@"Hey! Here you'd take a picture of the barcode! But you can't do that on the simulator. :)" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    [alertView show];
    [alertView release];
    
    
//    UIImagePickerController * picker = [[[UIImagePickerController alloc] init] autorelease];
//    picker.delegate = self;	
//    picker.sourceType = UIImagePickerControllerSourceTypeCamera;
//    picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;

//    [self presentModalViewController:picker animated:YES];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info 
{
	[picker dismissModalViewControllerAnimated:YES];
	imageView = [info objectForKey:@"UIImagePickerControllerOriginalImage"];
}

#pragma mark keyboard
- (void)keyboardWillShow:(NSNotification *)notif
{
	self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone 
																							target:self action:@selector(doneButtonHit:)] autorelease];
	//	self.navigationItem.hidesBackButton = YES;
}

- (void)keyboardDidShow:(NSNotification *)notif
{
}

- (void)keyboardWillHide:(NSNotification *)notif
{
	self.navigationItem.rightBarButtonItem = nil;
    
	self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"Submit", @"Submit") 
																			   style:UIBarButtonItemStyleDone target:self action:@selector(doneButtonHit:)] autorelease];
}

@end
