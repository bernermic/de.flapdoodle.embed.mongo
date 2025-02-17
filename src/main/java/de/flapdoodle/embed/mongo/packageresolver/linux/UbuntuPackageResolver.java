/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.packageresolver.*;
import de.flapdoodle.embed.process.config.store.DistributionPackage;
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.config.store.ImmutableFileSet;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Optional;

public class UbuntuPackageResolver implements PackageFinder, HasPlatformMatchRules {

  private final Command command;
  private final ImmutablePlatformMatchRules rules;

  public UbuntuPackageResolver(Command command) {
    this.command = command;
    this.rules = rules(command);
  }

	@Override
	public PlatformMatchRules rules() {
		return rules;
	}

	@Override
  public Optional<DistributionPackage> packageFor(Distribution distribution) {
    return rules.packageFor(distribution);
  }

	private static PlatformMatch match(BitSize bitSize, CPUType cpuType, UbuntuVersion... versions) {
		return PlatformMatch.withOs(OS.Linux).withBitSize(bitSize).withCpuType(cpuType)
			.withVersion(versions);
	}

  private static ImmutablePlatformMatchRules rules(Command command) {
    ImmutableFileSet fileSet = FileSet.builder().addEntry(FileType.Executable, command.commandName()).build();

		DistributionMatch ubuntu18xxArmMongoVersions = DistributionMatch.any(
			VersionRange.of("5.0.5", "5.0.5"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.18", "4.2.18"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3")
		);

		PlatformMatchRule ubuntu1804arm = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_18_10, UbuntuVersion.Ubuntu_19_04, UbuntuVersion.Ubuntu_19_10,
						UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10)
						.andThen(ubuntu18xxArmMongoVersions
						)
					)
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu1804-{version}.tgz")
							.build())
					.build();

			PlatformMatchRule tools_ubuntu1804arm = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.ARM,
							UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_18_10, UbuntuVersion.Ubuntu_19_04, UbuntuVersion.Ubuntu_19_10,
							UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10
						).andThen(ubuntu18xxArmMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu1804-arm64-{tools.version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu18xxMongoVersions = DistributionMatch.any(
			VersionRange.of("5.0.5", "5.0.5"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.18", "4.2.18"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3"),
			VersionRange.of("4.0.1", "4.0.27"),
			VersionRange.of("3.6.20", "3.6.23")
		);
		PlatformMatchRule ubuntu1804x64 = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.X86,
							UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_18_10, UbuntuVersion.Ubuntu_19_04, UbuntuVersion.Ubuntu_19_10,
							UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10
						).andThen(ubuntu18xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1804-{version}.tgz")
							.build())
					.build();

			PlatformMatchRule tools_ubuntu1804x64 = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.X86,
							UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_18_10, UbuntuVersion.Ubuntu_19_04, UbuntuVersion.Ubuntu_19_10,
							UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10
						).andThen(ubuntu18xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu1804-x86_64-{tools.version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu20xxMongoVersions = DistributionMatch.any(
			VersionRange.of("5.0.5", "5.0.5"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9")
		);
		
		PlatformMatchRule ubuntu2004arm = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10).andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2004-{version}.tgz")
							.build())
					.build();

			PlatformMatchRule tools_ubuntu2004arm = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10)
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu2004-arm64-{tools.version}.tgz")
							.build())
					.build();

			PlatformMatchRule ubuntu2004x64 = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10)
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2004-{version}.tgz")
							.build())
					.build();

			PlatformMatchRule tools_ubuntu2004x64 = PlatformMatchRule.builder()
					.match(match(BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10)
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu2004-x86_64-{tools.version}.tgz")
							.build())
					.build();

			switch (command) {
					case MongoDump:
					case MongoImport:
					case MongoRestore:
							return PlatformMatchRules.empty()
									.withRules(
											tools_ubuntu2004arm, tools_ubuntu2004x64,
											tools_ubuntu1804arm, tools_ubuntu1804x64
									);
			}

    return PlatformMatchRules.empty()
            .withRules(
                    ubuntu2004arm, ubuntu2004x64,
                    ubuntu1804arm, ubuntu1804x64
            );
  }
}
