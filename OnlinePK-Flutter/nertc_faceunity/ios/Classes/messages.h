// Copyright (c) 2022 NetEase, Inc. All rights reserved.
// Use of this source code is governed by a MIT license that can be
// found in the LICENSE file.
// Autogenerated from Pigeon (v0.3.0), do not edit directly.
// See also: https://pub.dev/packages/pigeon
#import <Foundation/Foundation.h>
@protocol FlutterBinaryMessenger;
@class FlutterError;
@class FlutterStandardTypedData;

NS_ASSUME_NONNULL_BEGIN

@class NECreateFaceUnityRequest;
@class NEFUInt;
@class NEFUDouble;
@class NEFUString;
@class SetFaceUnityParamsRequest;

@interface NECreateFaceUnityRequest : NSObject
@property(nonatomic, strong, nullable) FlutterStandardTypedData *beautyKey;
@property(nonatomic, copy, nullable) NSString *logDir;
@property(nonatomic, strong, nullable) NSNumber *logLevel;
@end

@interface NEFUInt : NSObject
@property(nonatomic, strong, nullable) NSNumber *value;
@end

@interface NEFUDouble : NSObject
@property(nonatomic, strong, nullable) NSNumber *value;
@end

@interface NEFUString : NSObject
@property(nonatomic, copy, nullable) NSString *value;
@end

@interface SetFaceUnityParamsRequest : NSObject
@property(nonatomic, strong, nullable) NSNumber *filterLevel;
@property(nonatomic, strong, nullable) NSNumber *colorLevel;
@property(nonatomic, strong, nullable) NSNumber *redLevel;
@property(nonatomic, strong, nullable) NSNumber *blurLevel;
@property(nonatomic, strong, nullable) NSNumber *eyeBright;
@property(nonatomic, strong, nullable) NSNumber *eyeEnlarging;
@property(nonatomic, strong, nullable) NSNumber *cheekThinning;
@property(nonatomic, copy, nullable) NSString *filterName;
@end

@protocol NEFTFaceUnityEngineApi
- (nullable NEFUInt *)create:(NECreateFaceUnityRequest *)input
                       error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setFilterLevel:(NEFUDouble *)input
                               error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setFilterName:(NEFUString *)input
                              error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setColorLevel:(NEFUDouble *)input
                              error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setRedLevel:(NEFUDouble *)input
                            error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setBlurLevel:(NEFUDouble *)input
                             error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setEyeEnlarging:(NEFUDouble *)input
                                error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setCheekThinning:(NEFUDouble *)input
                                 error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setEyeBright:(NEFUDouble *)input
                             error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)setMultiFUParams:(SetFaceUnityParamsRequest *)input
                                 error:(FlutterError *_Nullable *_Nonnull)error;
- (nullable NEFUInt *)release:(FlutterError *_Nullable *_Nonnull)error;
@end

extern void NEFTFaceUnityEngineApiSetup(id<FlutterBinaryMessenger> binaryMessenger,
                                        id<NEFTFaceUnityEngineApi> _Nullable api);

NS_ASSUME_NONNULL_END
