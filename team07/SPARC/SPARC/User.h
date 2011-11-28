//
//  User.h
//  SPARC
//
//  Created by Michael McEvoy on 8/27/11.
//  Copyright 2011 MUSC. All rights reserved.
//

typedef enum {
    kNotAssigned,
    kCustomer,
    kTeamMember,
    kBoth
} UserType;

#import <Foundation/Foundation.h>

@interface User : NSObject {
    
    NSString *ivEmailAddress;
    NSString *ivFirstName;
    NSString *ivLastName;
    NSString *ivUserName;

    UserType ivUserType;
    
}

@property (nonatomic, retain) NSString *EmailAddress;
@property (nonatomic, retain) NSString *FirstName;
@property (nonatomic, retain) NSString *LastName;
@property (nonatomic, retain) NSString *UserName;

@property (nonatomic, assign) UserType UserType;

@end
