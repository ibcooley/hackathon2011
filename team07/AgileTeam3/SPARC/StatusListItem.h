//
//  StatusListItem.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface StatusListItem : NSObject {
    
    NSDate *ivDeadline;
    NSDate *ivLastUpdate;
    
    NSString *ivDescription;
    NSString *ivItemStatus;
    NSString *ivItemType;
    
}

@property (nonatomic, retain) NSDate *Deadline;
@property (nonatomic, retain) NSDate *LastUpdate;

@property (nonatomic, retain) NSString *Description;
@property (nonatomic, retain) NSString *ItemStatus;
@property (nonatomic, retain) NSString *ItemType;

@end