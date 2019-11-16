[1mdiff --git a/parent/addons/pom.xml b/parent/addons/pom.xml[m
[1mindex f639fe8..21f8fb9 100644[m
[1m--- a/parent/addons/pom.xml[m
[1m+++ b/parent/addons/pom.xml[m
[36m@@ -12,6 +12,11 @@[m
 	<artifactId>addons</artifactId>[m
 [m
 	<dependencies>[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>com.pim.stars</groupId>[m
[32m+[m			[32m<artifactId>base</artifactId>[m
[32m+[m		[32m</dependency>[m
[32m+[m
 		<dependency>[m
 			<groupId>org.springframework.boot</groupId>[m
 			<artifactId>spring-boot-starter-actuator</artifactId>[m
[36m@@ -22,11 +27,6 @@[m
 			<artifactId>spring-boot-starter-test</artifactId>[m
 			<scope>test</scope>[m
 		</dependency>[m
[31m-[m
[31m-		<dependency>[m
[31m-			<groupId>com.pim.stars</groupId>[m
[31m-			<artifactId>base</artifactId>[m
[31m-		</dependency>[m
 	</dependencies>[m
 [m
 </project>[m
\ No newline at end of file[m
[1mdiff --git a/parent/addons/src/main/java/com/pim/stars/mineral/imp/MineralProperties.java b/parent/addons/src/main/java/com/pim/stars/mineral/imp/MineralProperties.java[m
[1mindex 6c01a67..f90148d 100644[m
[1m--- a/parent/addons/src/main/java/com/pim/stars/mineral/imp/MineralProperties.java[m
[1m+++ b/parent/addons/src/main/java/com/pim/stars/mineral/imp/MineralProperties.java[m
[36m@@ -2,10 +2,11 @@[m [mpackage com.pim.stars.mineral.imp;[m
 [m
 import java.util.List;[m
 [m
[32m+[m[32mimport javax.validation.constraints.Positive;[m[41m[m
[32m+[m[41m[m
 import org.springframework.boot.context.properties.ConfigurationProperties;[m
 import org.springframework.boot.context.properties.EnableConfigurationProperties;[m
 import org.springframework.context.annotation.PropertySource;[m
[31m-import org.springframework.lang.NonNull;[m
 import org.springframework.stereotype.Component;[m
 import org.springframework.validation.annotation.Validated;[m
 [m
[36m@@ -18,9 +19,8 @@[m [mpublic class MineralProperties {[m
 [m
 	private List<String> typeIds;[m
 	private int numberOfMinesToStartWith;[m
[31m-	@NonNull[m
[32m+[m[41m[m
 	private int baseConcentration;[m
[31m-	@NonNull[m
 	private int fractionalMiningPrecision;[m
 	private int homeWorldMinimumConcentration;[m
 [m
[36m@@ -50,6 +50,7 @@[m [mpublic class MineralProperties {[m
 		this.numberOfMinesToStartWith = numberOfMinesToStartWith;[m
 	}[m
 [m
[32m+[m	[32m@Positive[m[41m[m
 	public int getBaseConcentration() {[m
 		return baseConcentration;[m
 	}[m
[36m@@ -58,6 +59,7 @@[m [mpublic class MineralProperties {[m
 		this.baseConcentration = baseConcentration;[m
 	}[m
 [m
[32m+[m	[32m@Positive[m[41m[m
 	public int getFractionalMiningPrecision() {[m
 		return fractionalMiningPrecision;[m
 	}[m
[36m@@ -74,11 +76,10 @@[m [mpublic class MineralProperties {[m
 		this.homeWorldMinimumConcentration = homeWorldMinimumConcentration;[m
 	}[m
 [m
[32m+[m	[32m@Validated[m[41m[m
 	public static class RaceMiningSettings {[m
 [m
[31m-		@NonNull[m
 		private int mineProductionCost;[m
[31m-		@NonNull[m
 		private double mineEfficiency;[m
 [m
 		public RaceMiningSettings(final RaceMiningSettings defaultSettings) {[m
[36m@@ -91,6 +92,7 @@[m [mpublic class MineralProperties {[m
 			super();[m
 		}[m
 [m
[32m+[m		[32m@Positive[m[41m[m
 		public int getMineProductionCost() {[m
 			return mineProductionCost;[m
 		}[m
[36m@@ -99,6 +101,7 @@[m [mpublic class MineralProperties {[m
 			this.mineProductionCost = mineProductionCost;[m
 		}[m
 [m
[32m+[m		[32m@Positive[m[41m[m
 		public double getMineEfficiency() {[m
 			return mineEfficiency;[m
 		}[m
[1mdiff --git a/parent/pom.xml b/parent/pom.xml[m
[1mindex 15f6ad9..743df45 100644[m
[1m--- a/parent/pom.xml[m
[1m+++ b/parent/pom.xml[m
[36m@@ -62,6 +62,12 @@[m
 				</exclusions>[m
 			</dependency>[m
 [m
[32m+[m			[32m<dependency>[m
[32m+[m				[32m<groupId>org.springframework.boot</groupId>[m
[32m+[m				[32m<artifactId>spring-boot-starter-validation</artifactId>[m
[32m+[m				[32m<version>${spring.boot.version}</version>[m
[32m+[m			[32m</dependency>[m
[32m+[m
 			<dependency>[m
 				<!-- This dependency comes with the above BOM already, but it is listed here explicitly, so that JUnit 4 can be excluded. -->[m
 				<groupId>org.springframework.boot</groupId>[m
[36m@@ -118,6 +124,11 @@[m
 			<optional>true</optional>[m
 		</dependency>[m
 [m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.springframework.boot</groupId>[m
[32m+[m			[32m<artifactId>spring-boot-starter-validation</artifactId>[m
[32m+[m		[32m</dependency>[m
[32m+[m
 		<dependency>[m
 			<groupId>org.springframework.boot</groupId>[m
 			<artifactId>spring-boot-configuration-processor</artifactId>[m
