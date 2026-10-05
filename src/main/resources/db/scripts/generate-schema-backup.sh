#!/bin/bash
# Script to generate PostgreSQL schema backups
# Usage: ./generate-schema-backup.sh [schema-only|full]

# Configuration (can be overridden with environment variables)
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-crmdb}"
DB_USER="${DB_USER:-crmuser}"
DB_SCHEMA="${DB_SCHEMA:-crm}"

# Backup directory
BACKUP_DIR="$(dirname "$0")/backups"
mkdir -p "$BACKUP_DIR"

# Timestamp
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

# Backup type (default: schema-only)
BACKUP_TYPE="${1:-schema-only}"

echo "🔄 Starting PostgreSQL backup..."
echo "   Database: ${DB_NAME}"
echo "   Schema: ${DB_SCHEMA}"
echo "   Type: ${BACKUP_TYPE}"

if [ "$BACKUP_TYPE" = "schema-only" ]; then
    # Schema structure only (no data)
    OUTPUT_FILE="${BACKUP_DIR}/schema-backup-${TIMESTAMP}.sql"
    pg_dump -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" \
        --schema="$DB_SCHEMA" \
        --schema-only \
        --no-owner \
        --no-privileges \
        "$DB_NAME" > "$OUTPUT_FILE"

elif [ "$BACKUP_TYPE" = "full" ]; then
    # Full backup (structure + data)
    OUTPUT_FILE="${BACKUP_DIR}/full-backup-${TIMESTAMP}.sql"
    pg_dump -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" \
        --schema="$DB_SCHEMA" \
        --no-owner \
        --no-privileges \
        "$DB_NAME" > "$OUTPUT_FILE"
else
    echo "❌ Invalid backup type: $BACKUP_TYPE"
    echo "   Usage: $0 [schema-only|full]"
    exit 1
fi

if [ $? -eq 0 ]; then
    echo "✅ Backup completed successfully!"
    echo "   File: $OUTPUT_FILE"
    echo "   Size: $(du -h "$OUTPUT_FILE" | cut -f1)"

    # Keep only last 10 backups
    echo "🧹 Cleaning old backups (keeping last 10)..."
    ls -t "$BACKUP_DIR"/*.sql | tail -n +11 | xargs -r rm
else
    echo "❌ Backup failed!"
    exit 1
fi
