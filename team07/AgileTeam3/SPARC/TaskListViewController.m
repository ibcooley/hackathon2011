//
//  TaskListViewController.m
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import "TaskListViewController.h"

#import "StoryEditViewController.h"
#import "TaskViewController.h"

#import "Project.h"
#import "Story.h"
#import "Task.h"

@implementation TaskListViewController

@synthesize ConnectionData = ivConnectionData;
@synthesize CurrentProject = ivCurrentProject;
@synthesize CurrentStory = ivCurrentStory;
@synthesize CurrentTask = ivCurrentTask;
@synthesize Parser = ivParser;
@synthesize Pinwheel = ivPinwheel;
@synthesize SoapString = ivSoapString;
@synthesize TaskList = ivTaskList;
@synthesize TaskListSegmentedControl = ivTaskListSegmentedControl;
@synthesize TaskListTableView = ivTaskListTableView;
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
    [self setTaskList:array];
    [array release];
    // Web Service Call
    NSString *soapMessage = [NSString stringWithFormat:@"<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                             @"<s:Header />"
                             @"<s:Body>"
                             @"<GetTaskList xmlns=\"http://tempuri.org/\">"
                             @"<projectTitle>%@</projectTitle>"
                             @"<storyTitle>%@</storyTitle>"
                             @"</GetTaskList>"
                             @"</s:Body>"
                             @"</s:Envelope>", [[self CurrentProject] Title], [[self CurrentStory] Title]];
    NSLog(@"%@", [soapMessage stringByReplacingOccurrencesOfString:@"><" withString:@">\n<"]);
    NSString *fullAddress = @"http://216.69.156.62/AgileService/Service1.svc";
    NSURL *url = [NSURL URLWithString:fullAddress];
    NSMutableURLRequest *theRequest = [NSMutableURLRequest requestWithURL:url];
    NSString *msgLength = [NSString stringWithFormat:@"%d", [soapMessage length]];              
    [theRequest addValue: @"text/xml; charset=utf-8" forHTTPHeaderField:@"Content-Type"];       
    [theRequest addValue: msgLength forHTTPHeaderField:@"Content-Length"];
    [theRequest addValue: @"http://tempuri.org/IService1/GetTaskList" forHTTPHeaderField:@"Soapaction"];
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
    [self setTitle:[[self CurrentStory] Title]];
}

- (void)viewDidUnload
{
    [self setTaskListTableView:nil];
    [self setTaskListSegmentedControl:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
- (IBAction)TaskListSegmentedControlChanged:(id)sender {
    switch ([[self TaskListSegmentedControl] selectedSegmentIndex]) {
        case 0:
            //filter by new
            break;
        case 1:
            //filter by assigned to user
            break;
        case 2:
            //filter in Progress
            break;
        case 3:
            //filter by due date
            break;
        case 4:    
            //filter by status == done
            break;
        default:
            break;
    }
    
}

- (void)dealloc {
    [ivTaskListTableView release];
    [ivTaskListSegmentedControl release];
    [super dealloc];
}

#pragma mark UITableView Data Source 
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[self TaskList] count] + 2;
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
            [[cell textLabel] setText:@"Edit Story"];
            break;
        case 1:
            // Add New Story
            [[cell textLabel] setText:@"Add New Task"];
            [[cell imageView] setImage:[UIImage imageNamed:@"AddCellIconGreen.png"]];
            break;
        default:
        {
            int index = [indexPath row] - 2;
            Task *thisTask = [[self TaskList] objectAtIndex:index];
            [[cell textLabel] setText:[thisTask Description]];
            [[cell imageView] setImage:[UIImage imageNamed:@"TaskListIcon.png"]];
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
            // Edit Story Cell
        {
            StoryEditViewController *detailVC = [[StoryEditViewController alloc] initWithNibName:@"StoryEditViewController" bundle:nil];
            [detailVC setThisStory:[self CurrentStory]];
            [[self navigationController] pushViewController:detailVC animated:YES];
        }
            break;
        case 1:
            // Add New Task Cell
        {
            TaskViewController *detailVC = [[TaskViewController alloc] initWithNibName:@"TaskViewController" bundle:nil];
            [[self navigationController] pushViewController:detailVC animated:YES];
            [detailVC release];
        }
            break;
        default:
        {
            int index = [indexPath row] - 2;
            Task *thisTask = [[self TaskList] objectAtIndex:index];
            TaskViewController *detailVC = [[TaskViewController alloc] initWithNibName:@"TaskViewController" bundle:nil];
            [detailVC setThisStory:[self CurrentStory]];
            [detailVC setThisTask:thisTask];
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
    if ([elementName isEqualToString:@"TaskItem"]) {
        Task *task = [[Task alloc] init];
        [self setCurrentTask:task];
        [task release];
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string
{
    [[self SoapString] appendString:string];
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    if ([elementName isEqualToString:@"Description"]) {
        [[self CurrentTask] setDescription:[self SoapString]];
        [[self TaskList] addObject:[self CurrentStory]];
        [self setCurrentTask:nil];
        Task *task = [[Task alloc] init];
        [self setCurrentTask:task];
        [task release];
        [[self TaskListTableView] reloadData];
    } else if ([elementName isEqualToString:@"Deadline"]) {
        NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
        [[self CurrentTask] setDeadline:[formatter dateFromString:[self SoapString]]];
    } else if ([elementName isEqualToString:@"Status"]) {
        [[self CurrentTask] setStatus:[self SoapString]];
    }
}

- (void)parserDidEndDocument:(NSXMLParser *)parser
{
    [[self Pinwheel] stopAnimating];
}

@end
