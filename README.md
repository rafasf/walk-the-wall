# Walk the Wall

Focus the stand-up time in the **stories** and how we can deliver that value as
a team by going through them as a presentation.

## :ticket: Configuration

**Walk the Wall** needs to know a few things to put the board together. It
currently only supports Jira, and all properties are **required** at the moment.

| Property  | Purpose | What? |
| --- | --- | --- |
| `name` | Name of the product or project  | Any name you want |
| `base-url` | Jira's base url | Usually something like: https://company-jira.company.com |
| `jql` | JQL to retrieve the "issues" | Good place to start: _Board configuration > General > Filter Query_ |
| `token` | Reference for a environment variable | The content should be the base64 of "username:password" |
| `status-flow` | The board workflow order | _Board configuration > Columns (the blue pills)_ |


```edn
[
    {
     :name "Product name"
     :base-url "https://jira-server.com"
     :token "ENV_VAR_WITH_TOKEN"
     :jql "project = 'Your Project' AND Sprint in openSprints()"
     :status-flow ["Todo", "In Development" "Ready for Testing" "In Testing"]
    }
]
```
## :books: Usage

### :warning: Important

- The image exposes port **3000**
- The image uses non-root user `1001`
    - Ensure files grant permission for that user (default is root)
- `CONFIG_PATH` should be set with the full path for the **configuration file**

Since you need to provide the **configuration file** and at least a couple of
**environment variables**, find the recommendations below.

### :whale: Build your own container

1. Create the configuration file, e.g. `wall_config.edn`
2. Create the `token(s)` using your user name and password
    
```
FROM rafasf/walk-the-wall:latest

ENV CONFIG_PATH /app_config/wall_config.edn

USER 1001
COPY ./wall_config.edn /app_config
```

Deploy in your internal network, providing the **environment variables** for
each of the product or projects you've configured.

### :computer: Run container image directly

1. Create the configuration file, e.g. `wall_config.edn`
2. Create the `token(s)` using your user name and password

```bash
docker run --user 1001 \
    --env CONFIG_PATH=/app_config/wall_config.edn
    --env ENV_VAR_WITH_TOKEN=<token-value> \
    -v `pwd`/resources:/app_config
    local/walk-the-wall:0.1.0
```

## :nerd_face: Story Card

The Story Card is a representation of a user or technical story that the team is
working. It provides enough visual cues for people to share their learnings,
challenges and ask for help.

The cues are:
1. Title
2. Feature
3. Identifier
4. Assignee
5. Status

## License

Check [LICENSE](./LICENSE)
