//
//  StoryListViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "StoryListViewController.h"

#import "ProjectViewController.h"
#import "StoryEditViewController.h"
#import "TaskListViewController.h"

#import "Project.h"
#import "Story.h"

@implementation StoryListViewController

@synthesize ConnectionData = ivConnectionData;
@synthesize CurrentProject = ivCurrentProject;
@synthesize CurrentStory = ivCurrentStory;
@synthesize FilterSegmentedControl = ivFilterSegmentedControl;
@synthesize Parser = ivParser;
@synthesize Pinwheel = ivPinwheel;
@synthesize SoapString = ivSoapString;
@synthesize StoryList = ivStoryList;
@synthesize SummaryTableView = ivSummaryTableView;
@synthesize TheConnection = ivTheConnection;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
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
    NSMutableArray *array = [[NSMutableArray alloc] init];
    [self setStoryList:array];
    [array release];
    //Create an instance of activity indicator view
    UIActivityIndicatorView * activityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:CGRectMake(0, 0, 20, 20)];
    //set the initial property
    [activityIndicator stopAnimating];
    [activityIndicator hidesWhenStopped];
    //Create an instance of Bar button item with custome view which is of activity indicator
    UIBarButtonItem * barButton = [[UIBarButtonItem alloc] initWithCustomView:activityIndicator];
    [[self navigationItem] setRightBarButtonItem:barButton];
    [activityIndicator startAnimating];
    [self setPinwheel:activityIndicator];
    [activityIndicator release];
    [barButton release];
    
    // Web Service Call
    NSString *soapMessage = [NSString stringWithFormat:@"<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                             @"<s:Header />"
                             @"<s:Body>"
                             @"<GetStoryList xmlns=\"http://tempuri.org/\">"
                             @"<project>%@</project>"
                             @"</GetStoryList>"
                             @"</s:Body>"
                             @"</s:Envelope>", [[self CurrentProject] Title]];
    NSLog(@"%@", [soapMessage stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSString *fullAddress = @"http://216.69.156.62/AgileService/Service1.svc";
    NSURL *url = [NSURL URLWithString:fullAddress];
    NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:url];
    NSString *msgLength = [NSString stringWithFormat:@"%d", [soapMessage length]];              
    [theRequest addValue: @"text/xml; charset=utf-8" forHTTPHeaderField:@"Content-Type"];       
    [theRequest addValue: msgLength forHTTPHeaderField:@"Content-Length"];
    [theRequest addValue: @"http://tempuri.org/IService1/GetStoryList" forHTTPHeaderField:@"Soapaction"];
    [theRequest setHTTPMethod:@"POST"];     
    [theRequest setHTTPBody: [soapMessage dataUsingEncoding:NSUTF8StringEncoding]];
    NSURLConnection *theConnection = [[NSURLConnection alloc] initWithRequest:theRequest delegate:self];
    [self setTheConnection:theConnection];
    if(theConnection) {
        NSMutableData *data = [[NSMutableData alloc] init];
        [self setConnectionData:data];
        [data release];
    }
    [theConnection release];
}

- (void)viewWillAppear:(BOOL)animated
{
    [[self navigationController] setNavigationBarHidden:NO animated:NO];
    [self setTitle:[[self CurrentProject] Title]];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [self setTitle:@"Back"];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    [self setSummaryTableView:nil];
    [self setFilterSegmentedControl:nil];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)NewButtonClick:(id)sender
{
    
}

- (IBAction)FilterSegmentedControlChanged:(id)sender 
{
    switch ([[self FilterSegmentedControl] selectedSegmentIndex]) {
        case 0:
            //filter by date
            break;
        case 1:
            //filter by assigned to user
            break;
        case 2:
            //filter for new, question, & ready for test
            break;
        case 3:
            //filter by due date
            break;
        case 4:    
        //filter by status = done
            break;
        default:
            break;
    }
}

- (void)dealloc
{
    [ivSummaryTableView release];
    [ivFilterSegmentedControl release];
    [super dealloc];
}

#pragma mark UITableView Data Source 
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self StoryList] count] + 2;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell"];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"Cell"];
    }
    // Set up the cell
    switch ([indexPath row]) {
        case 0:
            // Edit Project Title
            [[cell textLabel] setText:@"Edit Project Title"];
            break;
        case 1:
            // Add New Story
            [[cell textLabel] setText:@"Add New Story"];
            [[cell imageView] setImage:[UIImage imageNamed:@"AddCellIconGreen.png"]];
            break;
        default:
        {
            int index = [indexPath row] - 2;
            Story *thisStory = [[self StoryList] objectAtIndex:index];
            [[cell textLabel] setText:[thisStory Title]];
            [[cell detailTextLabel] setText:[thisStory Description]];
            [[cell imageView] setImage:[UIImage imageNamed:@"StoryListIcon.png"]];
        }
            break;
    }
    return cell;
}

#pragma mark UITableView Delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    switch ([indexPath row]) {
        case 0:
            // Edit Project Title
        {
            ProjectViewController *detailVC = [[ProjectViewController alloc] initWithNibName:@"ProjectViewController" bundle:nil];
            [detailVC setThisProject:[self CurrentProject]];
            [[self navigationController] pushViewController:detailVC animated:YES];
            [detailVC release];
        }
            break;
        case 1:
            // Add New Story
        {
            StoryEditViewController *detailVC = [[StoryEditViewController alloc] initWithNibName:@"StoryEditViewController" bundle:nil];
            [[self navigationController] pushViewController:detailVC animated:YES];
            [detailVC release];
        }
            break;
        default:
        {
            int index = [indexPath row] - 2;
            Story *thisStory = [[self StoryList] objectAtIndex:index];
            TaskListViewController *detailVC = [[TaskListViewController alloc] initWithNibName:@"TaskListViewController" bundle:nil];
            [detailVC setCurrentProject:[self CurrentProject]];
            [detailVC setCurrentStory:thisStory];
            [[self navigationController] pushViewController:detailVC animated:YES];
            [detailVC release];
        }
            break;
    }
}

#pragma mark - NSURLConnection Section
- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    [[self ConnectionData] appendData:data];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    NSLog(@"\n%@", [[[NSString alloc] initWithData:[self ConnectionData] encoding:NSUTF8StringEncoding] stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSXMLParser *parser = [[NSXMLParser alloc] initWithData:[self ConnectionData]];
    [parser setDelegate:self];
    [parser setShouldProcessNamespaces:YES];
    [self setParser:parser];
    [[self Parser] parse];
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    NSLog(@"\n%@", [[[NSString alloc] initWithData:[self ConnectionData] encoding:NSUTF8StringEncoding] stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSLog(@"%@", [error description]);
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Could Not Connect" message:@"I'm sorry, but the system is currently unreachable. Please check your network connectivity." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    [alert show];
    [alert release];
}

#pragma mark - NSXMLParser Delegate Section
- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict
{
    NSMutableString *string = [[NSMutableString alloc] init];
    [self setSoapString:string];
    [string release];
    if ([elementName isEqualToString:@"StoryItem"]) {
        Story *story = [[Story alloc] init];
        [self setCurrentStory:story];
        [story release];
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string
{
    [[self SoapString] appendString:string];
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    if ([elementName isEqualToString:@"Title"]) {
        [[self CurrentStory] setTitle:[self SoapString]];
        [[self StoryList] addObject:[self CurrentStory]];
        [self setCurrentStory:nil];
        Story *story = [[Story alloc] init];
        [self setCurrentStory:story];
        [story release];
        [[self SummaryTableView] reloadData];
    } else if ([elementName isEqualToString:@"Description"]) {
        [[self CurrentStory] setDescription:[self SoapString]];
    }
}

- (void)parserDidEndDocument:(NSXMLParser *)parser
{
    [[self Pinwheel] stopAnimating];
}


@end