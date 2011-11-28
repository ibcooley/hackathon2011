//
//  Project.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Project : NSObject {
    
    NSMutableArray *ivStories;
    NSMutableArray *ivUserList;
    
    NSString *ivTitle;
    
}

@property (nonatomic, retain) NSMutableArray *Stories;
@property (nonatomic, retain) NSMutableArray *UserList;

@property (nonatomic, retain) NSString *Title;

@end
