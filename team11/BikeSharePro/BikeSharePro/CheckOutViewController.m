//
//  CheckOutViewController.m
//  BikeSharePro
//
//  Created by Brian M. Criscuolo on 8/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "CheckOutViewController.h"
#import "CheckOutTableViewCell.h"
#import "BikeViewController.h"
#import "SerialNumberViewController.h"

@implementation CheckOutViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        _appDelegate = [[UIApplication sharedApplication] delegate];
        
//        self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc]initWithTitle:NSLocalizedString(@"Done", @"Done") style:UIBarButtonItemStyleBordered target:self action:@selector(doneButtonHit:)] autorelease];
        self.title = @"Bikes";
    }
    return self;
}

- (IBAction) doneButtonHit:(id) sender;
{
    [self.navigationController popViewControllerAnimated:YES];
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

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];

}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    
    if (_appDelegate.currentBike != nil)
        [self dismissModalViewControllerAnimated:YES];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    self.navigationItem.hidesBackButton = NO;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    if (section == 0)
        return 1;
    else if (section == 1)
        return [_appDelegate.bikeArray count];
    
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *baseIdentifier = @"Cell";
    static NSString *bikeIdentifier = @"BikeCell";
    
    if (indexPath.section == 0)
    {
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:baseIdentifier];
        if (cell == nil) {
            cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:baseIdentifier] autorelease];
        }

        cell.textLabel.text = @"Enter your bike's serial number";
        cell.textLabel.textAlignment = UITextAlignmentCenter;
        return cell;
    }
    else
    {
        CheckOutTableViewCell *cell = (CheckOutTableViewCell *) [tableView dequeueReusableCellWithIdentifier:bikeIdentifier];
        if (cell == nil) {
            cell = [[[CheckOutTableViewCell alloc] initWithFrame:CGRectZero reuseIdentifier:bikeIdentifier] autorelease];
        }

        NSDictionary *bikeDict = [_appDelegate.bikeArray objectAtIndex:indexPath.row];
        [cell setData:bikeDict];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        return cell;
    }
    // Configure the cell...
    return nil;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section == 0)
    {
        // Pass the selected object to the new view controller.
//        NSDictionary *bikeDict = [_appDelegate.bikeArray objectAtIndex:indexPath.row];
        SerialNumberViewController *serialController = [[SerialNumberViewController alloc] init];
//        serialController.bikeDict = bikeDict;
        [self.navigationController pushViewController:serialController animated:YES];
        [serialController release];
    }
    else
    {
        // Pass the selected object to the new view controller.
        NSDictionary *bikeDict = [_appDelegate.bikeArray objectAtIndex:indexPath.row];
        BikeViewController *bikeController = [[BikeViewController alloc] init];
        bikeController.bikeDict = bikeDict;
        [self.navigationController pushViewController:bikeController animated:YES];
        [bikeController release];
   }
 }

@end
