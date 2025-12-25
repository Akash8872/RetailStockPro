# Deployment Guide: AWS EC2

This guide outlines the steps to deploy the RetailStock Pro application to an AWS EC2 instance.

## Prerequisites
- An AWS Account.
- AWS CLI installed (optional, for advanced users).
- SSH Client (e.g., Terminal, PuTTY).

## Step 1: Launch an EC2 Instance

1.  **Log in to AWS Console** and navigate to **EC2**.
2.  Click **Launch Instance**.
3.  **Name**: `RetailStock-Server`
4.  **AMI**: Select **Amazon Linux 2023 AMI** (Free tier eligible).
5.  **Instance Type**: `t2.micro` (Free tier eligible) or `t3.small` for better performance.
6.  **Key Pair**: Select an existing key pair or create a new one (save the `.pem` file safely).
7.  **Network Settings**:
    *   Create a security group.
    *   Allow SSH traffic from **My IP**.
    *   Allow HTTP/HTTPS (optional).
    *   **IMPORTANT**: Add a custom TCP rule to allow port `8080` from `Anywhere (0.0.0.0/0)` so you can access the app.
8.  **Advanced Details** -> **User Data**:
    *   Copy and paste the content of `deployment/ec2-user-data.sh` into this field. This script automatically installs Docker and Docker Compose.
9.  Click **Launch Instance**.

## Step 2: Transfer Application Files

Once the instance is running (check "Instance State" is "Running"), you need to move your project files to the server.

You can use `scp` (Secure Copy Protocol) to transfer the files. Run this from your local project root:

```bash
# Replace 'your-key.pem' with your actual key file path
# Replace 'ec2-user@your-ec2-ip' with your instance's public DNS or IP

# secure the key first
chmod 400 your-key.pem

# Transfer files
# IMPORTANT: Check your connection username!
# For Amazon Linux: user is 'ec2-user'
# For Ubuntu: user is 'ubuntu'

# Example for Ubuntu:
scp -i "your-key.pem" -r docker-compose.yml Dockerfile pom.xml src/ ubuntu@<YOUR_EC2_PUBLIC_IP>:/home/ubuntu/retailstock/

# Example for Amazon Linux:
# scp -i "your-key.pem" -r docker-compose.yml Dockerfile pom.xml src/ ec2-user@<YOUR_EC2_PUBLIC_IP>:/home/ec2-user/retailstock/
```

*Alternatively, if your code is on GitHub, you can SSH into the server and `git clone` it.*

## Step 3: Run the Application

1.  **SSH into the instance**:
    ```bash
    # For Ubuntu
    ssh -i "your-key.pem" ubuntu@<YOUR_EC2_PUBLIC_IP>
    
    # For Amazon Linux
    # ssh -i "your-key.pem" ec2-user@<YOUR_EC2_PUBLIC_IP>
    ```

2.  **Navigate to the directory**:
    ```bash
    cd retailstock
    ```

    *If you used SCP, the files should be here. If you didn't create a folder in the SCP command, they might be in the root `~`.*

3.  **Start the application**:
    ```bash
    docker compose up -d --build
    ```

    *Note: This might take a few minutes as it builds the Maven project and PostgreSQL image.*

4.  **Verify**:
    Run `docker compose ps` to see if containers are up.

## Step 4: Access the Application

Open your browser and navigate to:
`http://<YOUR_EC2_PUBLIC_IP>:8080`

You should see the RetailStock Pro login page.
