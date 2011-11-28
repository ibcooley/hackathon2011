
#import "ReportViewController.h"
#import "CellTextView.h"

#define		kNumberOfSections		1

#define		kSectionDescription		0
#define		kSectionDescriptionRowCount		1
#define		kSectionDescriptionTextRow	0


@implementation ReportViewController

@synthesize	reportDescription = _reportDescription;
@synthesize textView = _textView;
@synthesize tableView = _tableView;
@synthesize textViewString = _textViewString;
@synthesize isNewInstanceOnView = isNewInstanceOnView;


// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
        isNewInstanceOnView = NO;
    }
    return self;
}


/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	self.title = NSLocalizedString(@"Report", @"Report");
 
    
    self.navigationItem.leftBarButtonItem = [[[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(cancelButtonHit:)] autorelease];

}

- (void)viewWillAppear:(BOOL)animated
{	
	// watch the keyboard so we can adjust the user interface if necessary.
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) 
												 name:UIKeyboardWillShowNotification object:self.view.window]; 
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardDidShow:) 
												 name:UIKeyboardDidShowNotification object:self.view.window]; 
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) 
												 name:UIKeyboardWillHideNotification object:self.view.window]; 

	self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"Submit", @"Submit") 
																			   style:UIBarButtonItemStyleDone target:self action:@selector(submitButtonHit:)] autorelease];

	if (self.textViewString && [self.textViewString isEqualToString:@""] == NO)
		self.textView.text = self.textViewString;
    
    [self.textView becomeFirstResponder];
	
}

- (void)viewDidAppear:(BOOL)animated
{
    if (self.isNewInstanceOnView)
        [self performSelector:@selector(cancelButtonHit:) withObject:nil afterDelay:0.0];
    
}
- (void)viewDidDisappear:(BOOL)animated
{	
	NSIndexPath*	selection = [_tableView indexPathForSelectedRow];
	if (selection)
		[_tableView deselectRowAtIndexPath:selection animated:YES];	
	
	[super viewDidDisappear:animated];
}


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
	if (_textViewString) [_textViewString release];
	if (_textView) [_textView release];
	if (_reportDescription) [_reportDescription release];
    if (_tableView) [_tableView release];
	[super dealloc];
}

#pragma mark UITableView

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
	return kNumberOfSections;
}

- (NSInteger)tableView:(UITableView *)table numberOfRowsInSection:(NSInteger)section
{
	NSInteger rowCount = 0;
	switch (section)
	{
		case kSectionDescription:
			rowCount = kSectionDescriptionRowCount;
			break;
	}
	return rowCount;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
	CGFloat	height = 44.0;
	switch (indexPath.section)
	{
		case kSectionDescription:
			height = 180;
			break;
	}
	return height;
}


- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
	NSString *sectionTitle = nil;
	switch (section)
	{
	}
	return sectionTitle;			
}


//- (NSString *)tableView:(UITableView *)tableView titleForFooterInSection:(NSInteger)section
//{
//	NSString *sectionFooter;
//	switch (section)
//	{
//		case kSectionDescription:
//			sectionFooter = NSLocalizedString(@"Tap above to enter your report description.", @"Tap above to enter your report description.");
//			break;
//		case kSectionLocation:
//			sectionFooter = NSLocalizedString(@"Tap above to use your current location or to select your location on a map.", @"Tap above to use your current location or to select your location on a map.");
//			break;
//		case kSectionPhoto:
//			sectionFooter = NSLocalizedString(@"Tap above to take a photo to include in your report.", @"Tap above to take a photo to include in your report.");
//			break;
//	}
//	return sectionFooter;
//}


// Row display. Implementers should *always* try to reuse cells by setting each cell's reuseIdentifier and querying for available reusable cells with dequeueReusableCellWithIdentifier:
// Cell gets various attributes set automatically based on table (separators) and data source (accessory views, editing controls)

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
	UITableViewCell *cell = nil;
	switch (indexPath.section)
	{
		case kSectionDescription:
			switch (indexPath.row)
			{
				case kSectionDescriptionTextRow:
				{
					cell = (CellTextView *)[tableView dequeueReusableCellWithIdentifier:kCellTextView_ID];
					if (cell == nil) {
						cell = (CellTextView *)[[[CellTextView alloc] initWithFrame:CGRectZero reuseIdentifier:kCellTextView_ID] autorelease];
						((CellTextView *)cell).view = [self create_UITextView];
					}
					if (self.textViewString && [self.textViewString isEqualToString:@""] == NO)
						self.textView.text = self.textViewString;
					
					cell.accessoryType = UITableViewCellAccessoryNone;
				}
					break;
			}
			break;
	}
	
	return cell;	
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	switch (indexPath.section)
	{
		case kSectionDescription:
			switch (indexPath.row)
		{
			case kSectionDescriptionTextRow:
				break;
		}
        break;
	}
	
}

- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath
{
	return UITableViewCellEditingStyleNone;
}

- (BOOL)tableView:(UITableView *)tableView shouldIndentWhileEditingRowAtIndexPath:(NSIndexPath *)indexPath
{
	return NO;
}

- (UITextView *)create_UITextView
{
	CGRect frame = CGRectMake(0.0, 0.0, 100.0, 100.0);
	
	UITextView *textView = [[[UITextView alloc] initWithFrame:frame] autorelease];
    textView.textColor = [UIColor blackColor];
    textView.font = [UIFont systemFontOfSize:13];
    textView.delegate = self;
    textView.backgroundColor = [UIColor whiteColor];
	
	textView.text = NSLocalizedString(@"Tell us about your bike problem. We'll be sure to get back to you ASAP!\n\n", @"Tell us about your bike problem. We'll be sure to get back to you ASAP!\n\n");
	textView.returnKeyType = UIReturnKeyDefault;
	textView.keyboardType = UIKeyboardTypeDefault;	// use the default type input method (entire keyboard)
	
	self.textView = textView;
	
	return textView;
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
																			   style:UIBarButtonItemStyleDone target:self action:@selector(submitButtonHit:)] autorelease];
}
	
- (IBAction) doneButtonHit:(id) sender
{
	[self.textView resignFirstResponder];
}

- (IBAction) submitButtonHit:(id) sender
{
	MFMailComposeViewController *controller = [[MFMailComposeViewController alloc] init];
    if (controller)
    {
        controller.mailComposeDelegate = self;
        [controller setToRecipients:[NSArray arrayWithObject:@"reports@bikeshare.com"]];
        [controller setSubject:NSLocalizedString(@"Bike Share Problem Report", @"Bike Share Problemt Report")];
        NSMutableString *body = [NSMutableString stringWithString:self.textView.text];
        [controller setMessageBody:body isHTML:NO];
        
        [self.navigationController presentModalViewController:controller animated:YES];
        [controller release];
    }
}

#pragma mark <UITextViewDelegate> Methods

- (BOOL)textViewShouldBeginEditing:(UITextView *)textView
{
	self.tableView.editing = YES;
	
	if ([self.textView.text isEqualToString:NSLocalizedString(@"Add description of incident here:\n\n", @"Add description of incident here:\n\n")])
		self.textView.text = @"";
	
	self.textViewString = self.textView.text; 

	BOOL beginEditing = YES;
//    // Update internal state.
//    if (beginEditing)
//		self.isInlineEditing = YES;
    return beginEditing;
}

- (void)textViewDidEndEditing:(UITextView *)textView
{
//    // Notify the cell delegate that editing ended.
//    if (self && [self respondsToSelector:@selector(cellDidEndEditing:)])
//	{
//        [self cellDidEndEditing:self];
//    }
    // Update internal state.
//    self.isInlineEditing = NO;
	self.tableView.editing = NO;
	
	if ([self.textView.text isEqualToString:@""])
		self.textView.text = NSLocalizedString(@"Add description of incident here:\n\n", @"Add description of incident here:\n\n");

	self.textViewString = self.textView.text; 

	[self.tableView reloadData];
}


#pragma mark MFMailCompose delegate methods
- (void)mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{	
	switch (result) {
		case MFMailComposeResultCancelled:
			break;
		case MFMailComposeResultSaved:
			break;
		case MFMailComposeResultSent:
		{
			UIAlertView *alert = [[[UIAlertView alloc]
								   initWithTitle:NSLocalizedString(@"Report Sent", @"Report Sent") 
								   message:NSLocalizedString(@"Your report has been sent sucessfully. Thank you!", @"Your report has been sent sucessfully. Thank you!") 
								   delegate:nil 
								   cancelButtonTitle: NSLocalizedString(@"OK", @"OK")
								   otherButtonTitles:nil] autorelease];
			[alert show]; 
            self.isNewInstanceOnView = YES;
		}			
			break;
		case MFMailComposeResultFailed:
		{
			UIAlertView *alert = [[[UIAlertView alloc]
								   initWithTitle:NSLocalizedString(@"Send Failed", @"Send Failed") 
								   message:NSLocalizedString(@"Your report could not be sent. Please try again.", @"Your report could not be sent. Please try again.") 
								   delegate:nil 
								   cancelButtonTitle: NSLocalizedString(@"OK", @"OK")
								   otherButtonTitles:nil] autorelease];
			[alert show];    
		}			
			break;
	}
	[self.textView resignFirstResponder];
	[self dismissModalViewControllerAnimated:YES];	
}

- (IBAction)cancelButtonHit:(id)sender;
{
    [self dismissModalViewControllerAnimated:YES];
}
@end
