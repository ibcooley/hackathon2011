//
//  Story.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Project;

@interface Story : NSObject {
    
    NSMutableArray *ivTaskList;
    
    NSString *ivDescription;
    NSString *ivStatus;
    NSString *ivTitle;
    
    Project *ivOwner;
    
}

@property (nonatomic, retain) NSMutableArray *TaskList;

@property (nonatomic, retain) NSString *Description;
@property (nonatomic, retain) NSString *Status;
@property (nonatomic, retain) NSString *Title;

@property (nonatomic, retain) Project *Owner;

@end