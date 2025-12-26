# Troubleshooting Guide

## GitHub Actions Billing Issues

### Error: "The job was not started because recent account payments have failed or your spending limit needs to be increased"

This error indicates a GitHub Actions billing or quota issue at the account level. This is **not a code problem** and cannot be fixed by making changes to the repository code.

#### Root Cause

This error occurs when:
- GitHub Actions billing information is missing or invalid
- Payment method on file has failed
- Monthly spending limit for GitHub Actions has been reached
- Account has billing issues preventing Actions from running

#### How to Resolve

**For Repository Owners:**

1. **Check Billing Settings**
   - Navigate to GitHub Settings: Click your profile picture → Settings
   - Select "Billing and plans" from the left sidebar
   - Review your current plan and spending limits

2. **Verify Payment Method**
   - In Billing settings, check "Payment information"
   - Ensure your payment method is valid and up to date
   - Update credit card or payment information if expired

3. **Review Spending Limits**
   - Check "Plans and usage" section
   - Look at "Actions & Packages" spending
   - If you've hit your spending limit, you can:
     - Wait until your billing cycle resets
     - Increase your spending limit
     - Upgrade to a higher GitHub plan

4. **Check for Failed Payments**
   - Review "Payment history" for any failed transactions
   - Resolve any outstanding payment issues
   - Contact GitHub Support if you see unexpected charges

5. **Free Tier Limits**
   - GitHub Free accounts have monthly minute limits for Actions
   - Private repositories: 2,000 minutes/month
   - Public repositories: Unlimited for standard runners
   - If exceeded, wait for next billing cycle or upgrade plan

#### For Contributors

If you're a contributor seeing this error:
- Contact the repository owner/maintainer
- They need to resolve the billing issue on their account
- There's nothing contributors can do code-wise to fix this

#### Additional Resources

- [GitHub Actions Billing Documentation](https://docs.github.com/en/billing/managing-billing-for-github-actions/about-billing-for-github-actions)
- [Managing Spending Limits](https://docs.github.com/en/billing/managing-billing-for-github-actions/managing-your-spending-limit-for-github-actions)
- [GitHub Support](https://support.github.com/)

---

## Other Common Issues

### Build Failures

If you're experiencing build failures unrelated to billing:
1. Check the build logs in the Actions tab
2. Ensure all dependencies are properly specified
3. Verify Java version compatibility (this project uses Java)
4. Check for any missing files or resources

### Running Locally

To run this project locally:
1. Ensure you have Java JDK installed
2. Open the project in your IDE (IntelliJ IDEA, Eclipse, etc.)
3. Build and run the main class
4. JSON files for data storage should be in the project root
