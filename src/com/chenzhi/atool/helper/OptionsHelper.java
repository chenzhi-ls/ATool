package com.chenzhi.atool.helper;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

import com.chenzhi.atool.util.AToolLog;
import com.chenzhi.atool.util.StringUtils;

/**
 * Created by chenzhi on 2018年3月22日
 */
@SuppressWarnings("deprecation")
public class OptionsHelper {

	private static String OPT_HELP_INFO = "h";
	private static String OPT_APK_DIR = "d";
	// private static String OPT_TARGET_CONTENT = "c";
	private static String OPT_MODIFY_PACKAGE = "mp";
	private static String OPT_MODIFY_VERSION_NAME = "mvn";
	private static String OPT_MODIFY_VERSION_CODE = "mvc";
	private static String OPT_MODIFY_NAME = "mn";
	private static String OPT_SHOW_INFO = "si";
	private static String OPT_KEEP_ICON = "ki";
	private static String OPT_KEEP_MANIFESTT = "kmt";
	private static String OPT_KEEP_MANIFESTX = "kmx";
	private static String OPT_SHOW_LAUNCH_ACT = "sla";
	private static String OPT_SHOW_VERSION_NAME = "svn";
	private static String OPT_SHOW_VERSION_CODE = "svc";
	private static String OPT_SHOW_PACKAGE_NAME = "spn";
	private static String OPT_SHOW_MIN_SDKVERSION = "smsv";
	private static String OPT_SHOW_TARGER_SDKVERSION = "stsv";
	private static String OPT_SHOW_APP_NAME = "san";

	private static String OPT_VALUE_APK_DIR = "apk_dir";
	private static String OPT_VALUE_HELP_INFO = "help_info";
	// private static String OPT_VALUE_TARGET_CONTENT = "content";
	private static String OPT_VALUE_MODIFY_PACKAGE = "modify_package";
	private static String OPT_VALUE_MODIFY_VERSION_NAME = "modify_version_name";
	private static String OPT_VALUE_MODIFY_VERSION_CODE = "modify_version_code";
	private static String OPT_VALUE_MODIFY_NAME = "modify-app_name";
	private static String OPT_VALUE_SHOW_INFO = "show-app-info";
	private static String OPT_VALUE_KEEP_ICON = "keep-icon";
	private static String OPT_VALUE_KEEP_MANIFESTT = "keep-manifest-txt";
	private static String OPT_VALUE_KEEP_MANIFESTX = "keep-manifest-xml";
	private static String OPT_VALUE_SHOW_LAUNCH_ACT = "show-laaunch-activity";
	private static String OPT_VALUE_SHOW_VERSION_NAME = "show-version-name";
	private static String OPT_VALUE_SHOW_VERSION_CODE = "show-version-code";
	private static String OPT_VALUE_SHOW_PACKAGE_NAME = "show-package-name";
	private static String OPT_VALUE_SHOW_MIN_SDKVERSION = "show-min-sdk-version";
	private static String OPT_VALUE_SHOW_TARGER_SDKVERSION = "show-targter-sdk-version";
	private static String OPT_VALUE_SHOW_APP_NAME = "show-app-name";

	public static void main(String[] args) {
		AToolLog.IS_SHOW_LOG = true;
		options(new String[] { "-d", "test.apk", "-mp", "com.test1" });
	}

	public static void options(String[] args) {
		AToolOptions(args);
	}

	private static void AToolOptions(String[] args) {
		Options atoolOptions = new Options();

		Option option = new Option(OPT_HELP_INFO, OPT_VALUE_HELP_INFO, false, "输出帮助信息");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_APK_DIR, OPT_VALUE_APK_DIR, true, "apk文件路径");
		option.setRequired(false);
		atoolOptions.addOption(option);

		// option = new Option(OPT_TARGET_CONTENT, OPT_VALUE_TARGET_CONTENT,
		// true, "将要替换的内容 如新的包名或者APP名称");
		// option.setRequired(false);
		// atoolOptions.addOption(option);

		option = new Option(OPT_MODIFY_PACKAGE, OPT_VALUE_MODIFY_PACKAGE, true, "修改APP的包名");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_MODIFY_VERSION_NAME, OPT_VALUE_MODIFY_VERSION_NAME, true, "修改APP版本名称");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_MODIFY_VERSION_CODE, OPT_VALUE_MODIFY_VERSION_CODE, true, "修改APP版本号");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_MODIFY_NAME, OPT_VALUE_MODIFY_NAME, true, "修改APP名称");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_INFO, OPT_VALUE_SHOW_INFO, false,
				"输出APP信息（包括：APP名称、包名、版本号、版本名称、支持的最低版本、目标版本、icon名称）");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_KEEP_ICON, OPT_VALUE_KEEP_ICON, false, "保存最清晰的icon到根目录");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_KEEP_MANIFESTT, OPT_VALUE_KEEP_MANIFESTT, false, "将AndroidManifest保存TXT文件");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_KEEP_MANIFESTX, OPT_VALUE_KEEP_MANIFESTX, false, "将AndroidManifest保存XML文件");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_LAUNCH_ACT, OPT_VALUE_SHOW_LAUNCH_ACT, false, "输出APP的启动页面");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_VERSION_NAME, OPT_VALUE_SHOW_VERSION_NAME, false, "输出APP的版本名称");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_VERSION_CODE, OPT_VALUE_SHOW_VERSION_CODE, false, "输出APP的版本代号");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_PACKAGE_NAME, OPT_VALUE_SHOW_PACKAGE_NAME, false, "输出APP的包名");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_MIN_SDKVERSION, OPT_VALUE_SHOW_MIN_SDKVERSION, false, "输出支持的最低版本");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_TARGER_SDKVERSION, OPT_VALUE_SHOW_TARGER_SDKVERSION, false, "输出目标版本");
		option.setRequired(false);
		atoolOptions.addOption(option);

		option = new Option(OPT_SHOW_APP_NAME, OPT_VALUE_SHOW_APP_NAME, false, "输出APP名称");
		option.setRequired(false);
		atoolOptions.addOption(option);

		handelCmdAction(args, atoolOptions);

	}

	private static void handelCmdAction(String[] args, Options atoolOptions) {
		try {
			String uAppPath = "";
			String uPackageName = "";
			String uAppName = "";
			String uVersionCode = "";
			String uVersionname = "";

			CommandLineParser parser = new PosixParser();
			CommandLine commandLine = parser.parse(atoolOptions, args);
			
			if (commandLine.getOptions().length <= 0) {
				AToolLog.info("参数不能为空！！！");
				showHelperInfo(atoolOptions);
				return;
			}

			if (commandLine.hasOption(OPT_HELP_INFO)) {
				showHelperInfo(atoolOptions);
				return;
			}

			if (commandLine.hasOption(OPT_APK_DIR)) {
				uAppPath = commandLine.getOptionValue(OPT_VALUE_APK_DIR);
			}
			if (StringUtils.isEmpty(uAppPath)) {
				AToolLog.info("APK路径不能为空！！！ 请查看帮助信息 -h");
				return;
			}
			if (!new File(uAppPath).exists()) {
				AToolLog.info("请检查APK路径是否存在：" + uAppPath);
				return;
			}

			if (commandLine.hasOption(OPT_MODIFY_PACKAGE)) {
				uPackageName = commandLine.getOptionValue(OPT_VALUE_MODIFY_PACKAGE);
			}
			if (commandLine.hasOption(OPT_MODIFY_NAME)) {
				uAppName = commandLine.getOptionValue(OPT_VALUE_MODIFY_NAME);
			}
			if (commandLine.hasOption(OPT_MODIFY_VERSION_CODE)) {
				uVersionCode = commandLine.getOptionValue(OPT_VALUE_MODIFY_VERSION_CODE);
			}
			if (commandLine.hasOption(OPT_MODIFY_VERSION_NAME)) {
				uVersionname = commandLine.getOptionValue(OPT_VALUE_MODIFY_VERSION_NAME);
			}
			modifyAppInfo(uAppPath, uPackageName, uAppName, uVersionCode, uVersionname);
			
			if (commandLine.hasOption(OPT_KEEP_ICON)) {
				ShowAppInfoHelper.keepTempAppIcon(uAppPath);
			}

			if (commandLine.hasOption(OPT_KEEP_MANIFESTT)) {
				ShowAppInfoHelper.keepTempManifestTxtFile(uAppPath);
			}
			
			if (commandLine.hasOption(OPT_KEEP_MANIFESTX)) {
				ShowAppInfoHelper.keepTempManifestXmlFile(uAppPath);
			}
			
			if (commandLine.hasOption(OPT_SHOW_APP_NAME)) {
				showInfo("名称", ShowAppInfoHelper.getAppName4Tf(uAppPath));
			}
			
			if (commandLine.hasOption(OPT_SHOW_INFO)) {
				ShowAppInfoHelper.showAppInfo(uAppPath);
			}
			
			if (commandLine.hasOption(OPT_SHOW_LAUNCH_ACT)) {
				showInfo("启动页面", ShowAppInfoHelper.getLaunchElementName(uAppPath));
			}
			
			if (commandLine.hasOption(OPT_SHOW_MIN_SDKVERSION)) {
				showInfo("支持的最小版本", ShowAppInfoHelper.getAppMinSdkVersion4Tf(uAppPath));
			}

			if (commandLine.hasOption(OPT_SHOW_PACKAGE_NAME)) {
				showInfo("包名", ShowAppInfoHelper.getAppPackageName4Tf(uAppPath));
			}
			
			if (commandLine.hasOption(OPT_SHOW_TARGER_SDKVERSION)) {
				showInfo("支持的目标版本", ShowAppInfoHelper.getAppTargetSdkVersion4Tf(uAppPath));
			}
			
			if (commandLine.hasOption(OPT_SHOW_VERSION_CODE)) {
				showInfo("版本代号", ShowAppInfoHelper.getAppVersionCode4Tf(uAppPath));;
			}
			
			if (commandLine.hasOption(OPT_SHOW_VERSION_NAME)) {
				showInfo("版本名称", ShowAppInfoHelper.getAppVersionName4Tf(uAppPath));
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			AToolLog.error("命令错误 请查看帮助信息 -h");
		}
	}

	private static void showHelperInfo(Options atoolOptions) {
		HelpFormatter hf = new HelpFormatter();
		hf.setWidth(110);
		// 打印使用帮助
		hf.printHelp("ATool", "--------------------------", atoolOptions, "--------------------------", true);
		// hf.printHelp("ATool", atoolOptions, true);
	}

	private static void modifyAppInfo(String uadir, String upname, String uaname, String uvcode, String uvname) {
		if (!StringUtils.isEmpty(uaname) || !StringUtils.isEmpty(upname) || !StringUtils.isEmpty(uvcode)
				|| !StringUtils.isEmpty(uvname)) {
			ModifyInfoHelper.getInstance().modifyAppInfo(uadir, uaname, upname, uvcode, uvname);
		}
	}

	@SuppressWarnings("unused")
	private static void showInfo(String msg) {
		showInfo("结果", msg);
	}
	
	private static void showInfo(String tag, String msg) {
		System.out.println("APP" + tag + "：" + msg);
	}
}
